package ar.edu.unq.dapp_api.model.builders;

import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;

public class CryptoCurrencyBuilder {
    private CryptoSymbol symbol = CryptoSymbol.BTCUSDT;
    private float price = 50000.0f;
    private String lastUpdateDateAndTime = "2023-10-01T10:00:00";

    public CryptoCurrencyBuilder withSymbol(CryptoSymbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public CryptoCurrencyBuilder withPrice(float price) {
        this.price = price;
        return this;
    }

    public CryptoCurrencyBuilder withLastUpdateDateAndTime(String lastUpdateDateAndTime) {
        this.lastUpdateDateAndTime = lastUpdateDateAndTime;
        return this;
    }

    public CryptoCurrency build() {
        return new CryptoCurrency(symbol, price, lastUpdateDateAndTime);
    }
}