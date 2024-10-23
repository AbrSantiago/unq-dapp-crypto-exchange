package ar.edu.unq.dapp_api.model.builders;

import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;

import java.math.BigDecimal;
import java.time.LocalTime;

public class CryptoCurrencyBuilder {
    private CryptoSymbol symbol = CryptoSymbol.BTCUSDT;
    private BigDecimal price = BigDecimal.valueOf(50000.0);
    private LocalTime lastUpdateDateAndTime = LocalTime.of(12, 0);

    public CryptoCurrencyBuilder withSymbol(CryptoSymbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public CryptoCurrencyBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CryptoCurrencyBuilder withLastUpdateDateAndTime(LocalTime lastUpdateDateAndTime) {
        this.lastUpdateDateAndTime = lastUpdateDateAndTime;
        return this;
    }

    public CryptoCurrency build() {
        return new CryptoCurrency(symbol, price, lastUpdateDateAndTime);
    }
}
