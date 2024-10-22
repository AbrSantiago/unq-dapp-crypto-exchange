package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.exception.InvalidTransactionStateException;
import ar.edu.unq.dapp_api.exception.TransactionCompletedException;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "operation_intent_id", nullable = false)
    @JsonManagedReference
    private OperationIntent operationIntent;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @Setter
    private TransactionStatus status;
    private LocalDateTime startTime;

    public Transaction(OperationIntent operationIntent, User seller, User buyer) {
        this.operationIntent = operationIntent;
        this.seller = seller;
        this.buyer = buyer;
        this.status = TransactionStatus.PENDING;
        this.startTime = LocalDateTime.now();
    }

    public Transaction() {}

    public void completeTransfer() {
        if (this.status.equals(TransactionStatus.PENDING)) {
            this.status = TransactionStatus.TRANSFERRED;
        } else {
            throw new TransactionCompletedException("Transaction already completed");
        }
    }

    public void confirmReception() {
        if (this.status.equals(TransactionStatus.TRANSFERRED)) {
            this.status = TransactionStatus.CONFIRMED;
            int points = this.calculatePoints();
            this.seller.addPoints(points);
            this.seller.addOperation();
            this.buyer.addPoints(points);
            this.buyer.addOperation();
            this.operationIntent.close();
        } else {
            throw new InvalidTransactionStateException("Transaction cannot be confirmed because it is not transferred");
        }
    }

    private int calculatePoints() {
        Duration duration = Duration.between(this.startTime, LocalDateTime.now());
        long minutes = duration.toMinutes();
        return minutes <= 30 ? 10 : 5;
    }

    public String getDirection() {
        if (this.operationIntent.getType().equals(IntentionType.SELL)) {
            return seller.getCvu();
        }
        return buyer.getWalletAddress();
    }

    public void cancelTransaction() {
        this.status = TransactionStatus.CANCELLED;
        this.operationIntent.close();
    }

    public int cancelByUserPoints() {
        return 20;
    }

    public boolean isCanceled() {
        return this.status.equals(TransactionStatus.CANCELLED);
    }
}