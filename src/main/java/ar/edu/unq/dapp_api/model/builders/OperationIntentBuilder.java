package ar.edu.unq.dapp_api.model.builders;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;

import java.math.BigDecimal;

public class OperationIntentBuilder {
    private CryptoSymbol symbol = CryptoSymbol.BTCUSDT;
    private BigDecimal cryptoAmount = BigDecimal.valueOf(1);
    private BigDecimal cryptoPrice = BigDecimal.valueOf(50000);
    private BigDecimal operationARSAmount = BigDecimal.valueOf(50000);
    private User user = new UserBuilder().withId(1L).build();
    private IntentionType type = IntentionType.BUY;

    public OperationIntentBuilder withSymbol(CryptoSymbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public OperationIntentBuilder withCryptoAmount(BigDecimal cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
        return this;
    }

    public OperationIntentBuilder withCryptoPrice(BigDecimal cryptoPrice) {
        this.cryptoPrice = cryptoPrice;
        return this;
    }

    public OperationIntentBuilder withOperationARSAmount(BigDecimal operationARSAmount) {
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
