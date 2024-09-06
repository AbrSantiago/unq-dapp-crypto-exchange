package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalTime;

@Getter
public class Transaction {
    private final OperationIntent operationIntent;
    private final User seller;
    private final User buyer;
    @Setter
    private TransactionStatus status;
    private final LocalTime startTime;

    public Transaction(OperationIntent operationIntent, User seller, User buyer) {
        this.operationIntent = operationIntent;
        this.seller = seller;
        this.buyer = buyer;
        this.status = TransactionStatus.PENDING;
        this.startTime = LocalTime.now();
    }

    public void completeTransfer() {
        if (this.status.equals(TransactionStatus.PENDING)) {
            this.status = TransactionStatus.TRANSFERRED;
        } else {
            throw new IllegalArgumentException("Transaction already completed");
        }
    }

    public void confirmReception() {
        if (this.status.equals(TransactionStatus.TRANSFERRED)) {
            this.status = TransactionStatus.CONFIRMED;
            int points = this.calculatePoints();
            this.seller.addPoints(points);
            this.buyer.addPoints(points);
            this.operationIntent.close();
        } else {
            throw new IllegalArgumentException("Transaction already completed");
        }
    }

    private int calculatePoints() {
        Duration duration = Duration.between(this.startTime, LocalTime.now());
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
}