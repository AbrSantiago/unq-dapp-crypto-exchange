package ar.edu.unq.dapp_api.model.builders;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;

public class OperationIntentBuilder {
    private CryptoSymbol symbol = CryptoSymbol.BTCUSDT;
    private Long cryptoAmount = 1L;
    private Long cryptoPrice = 50000L;
    private Long operationARSAmount = 50000L;
    private User user = new UserBuilder().withId(1L).build();
    private IntentionType type = IntentionType.BUY;

    public OperationIntentBuilder withSymbol(CryptoSymbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public OperationIntentBuilder withCryptoAmount(Long cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
        return this;
    }

    public OperationIntentBuilder withCryptoPrice(Long cryptoPrice) {
        this.cryptoPrice = cryptoPrice;
        return this;
    }

    public OperationIntentBuilder withOperationARSAmount(Long operationARSAmount) {
        this.operationARSAmount = operationARSAmount;
        return this;
    }

    public OperationIntentBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public OperationIntentBuilder withType(IntentionType type) {
        this.type = type;
        return this;
    }

    public OperationIntent build() {
        if (type == null) {
            throw new IllegalArgumentException("IntentionType cannot be null");
        }
        return new OperationIntent(symbol, cryptoAmount, cryptoPrice, operationARSAmount, user, type);
    }
}