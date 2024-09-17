package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OperationIntent {
    private final CryptoSymbol symbol;
    private final Long cryptoAmount;
    private final Long cryptoPrice;
    private final Long operationARSAmount;
    private final User user;
    private final IntentionType type;
    private final LocalDateTime dateTime;
    private OperationStatus status;
    private Transaction transaction;

    public OperationIntent(CryptoSymbol symbol, Long cryptoAmount, Long cryptoPrice, Long operationARSAmount, User user, IntentionType type) {
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationARSAmount = operationARSAmount;
        this.user = user;
        this.type = type;
        this.dateTime = LocalDateTime.now();
        this.status = OperationStatus.OPEN;
    }

    public Transaction generateTransaction(User interestedUser, Double currentPrice) {
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