package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
@Getter
@Setter
@Builder
@Entity
public class CryptoCurrency implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private CryptoSymbol symbol;
    private BigDecimal price;
    private LocalTime lastUpdateDateAndTime;

    public CryptoCurrency() {}

    public CryptoCurrency(CryptoSymbol symbol, BigDecimal price, LocalTime lastUpdateDateAndTime) {
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