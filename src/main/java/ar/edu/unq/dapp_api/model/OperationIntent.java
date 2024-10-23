package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.exception.IllegalOperationException;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class OperationIntent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CryptoSymbol symbol;

    private BigDecimal cryptoAmount;
    private BigDecimal cryptoPrice;
    private BigDecimal operationARSAmount;
    private IntentionType type;
    private LocalDateTime dateTime;
    private OperationStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @OneToOne(mappedBy = "operationIntent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Transaction transaction;

    public OperationIntent(CryptoSymbol symbol, BigDecimal cryptoAmount, BigDecimal cryptoPrice, BigDecimal operationARSAmount, User user, IntentionType type) {
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationARSAmount = operationARSAmount;
        this.user = user;
        this.type = type;
        this.dateTime = LocalDateTime.now();
        this.status = OperationStatus.OPEN;
    }

    public OperationIntent() {
    }

    public Transaction generateTransaction(User interestedUser, BigDecimal currentPrice) {
        if (this.status.equals(OperationStatus.CLOSED)) {
            throw new IllegalOperationException("Cannot generate transaction from closed operation intent");
        }
        if (this.status.equals(OperationStatus.IN_PROCESS)) {
            throw new IllegalOperationException("Cannot generate transaction from in process operation intent");
        }
        if (interestedUser.getId().equals(this.getUser().getId())) {
            throw new IllegalArgumentException("User cannot buy/sell to himself");
        }
        if (this.type.equals(IntentionType.BUY)) {
            transaction = new Transaction(this, interestedUser, this.getUser());
        } else {
            transaction = new Transaction(this, this.getUser(), interestedUser);
        }
        this.validateTransaction(currentPrice);
        this.status = OperationStatus.IN_PROCESS;
        return transaction;
    }

    public void validateTransaction(BigDecimal currentPrice) {
        if (checkCancelTransaction(currentPrice)) {
            this.transaction.cancelTransaction();
        }
    }

    public boolean checkCancelTransaction(BigDecimal currentPrice) {
        BigDecimal lowerBound = currentPrice.subtract(currentPrice.multiply(BigDecimal.valueOf(0.05)));
        BigDecimal upperBound = currentPrice.add(currentPrice.multiply(BigDecimal.valueOf(0.05)));
        return this.cryptoPrice.compareTo(lowerBound) < 0 || this.cryptoPrice.compareTo(upperBound) > 0;
    }

    public void close() {
        this.status = OperationStatus.CLOSED;
    }
}
