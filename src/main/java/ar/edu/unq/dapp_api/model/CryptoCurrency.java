package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@Builder
public class CryptoCurrency implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private CryptoSymbol symbol;
    private Float price;
    private String lastUpdateDateAndTime;

    public CryptoCurrency() {
    }

    public CryptoCurrency(CryptoSymbol symbol, Float price, String lastUpdateDateAndTime) {
        this.symbol = symbol;
        this.price = price;
        this.lastUpdateDateAndTime = lastUpdateDateAndTime;
    }

    @Override
    public String toString() {
        return "CryptoCurrency [symbol=" + symbol + ", price=" + price + ", lastUpdateDateAndTime="
                + lastUpdateDateAndTime + "]";
    }
}