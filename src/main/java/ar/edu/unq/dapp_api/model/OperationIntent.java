package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.exception.IllegalOperationException;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class OperationIntent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CryptoSymbol symbol;
    private Long cryptoAmount;
    private Float cryptoPrice;
    private Long operationARSAmount;
    private IntentionType type;
    private LocalDateTime dateTime;
    private OperationStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "operationIntent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Transaction transaction;



    public OperationIntent(CryptoSymbol symbol, Long cryptoAmount, Float cryptoPrice, Long operationARSAmount, User user, IntentionType type) {
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


    public Transaction generateTransaction(User interestedUser, Double currentPrice) {
        if (this.status.equals(OperationStatus.CLOSED)) {
            throw new IllegalOperationException("Cannot generate transaction from closed operation intent");
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
        return transaction;
    }

    private void validateTransaction(Double currentPrice) {
        if (checkCancelTransaction(currentPrice)) {
                this.transaction.cancelTransaction();
        }
    }

    private boolean checkCancelTransaction(Double currentPrice) {
        return this.cryptoPrice < (currentPrice - currentPrice * 0.05) || this.cryptoPrice > (currentPrice + currentPrice * 0.05);
    }

    public void close() {
        this.status = OperationStatus.CLOSED;
    }

}