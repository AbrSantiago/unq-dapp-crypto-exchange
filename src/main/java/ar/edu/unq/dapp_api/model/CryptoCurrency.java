package ar.edu.unq.dapp_api.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class CryptoCurrency implements Serializable {
    private static final long serialVersionUID = 1L;

    private String symbol;
    private Float price;
    private String lastUpdateDateAndTime;

    public CryptoCurrency() {
    }

    public CryptoCurrency(String symbol, Float price, String lastUpdateDateAndTime) {
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