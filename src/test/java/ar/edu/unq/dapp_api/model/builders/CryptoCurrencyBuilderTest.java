package ar.edu.unq.dapp_api.model.builders;

import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CryptoCurrencyBuilderTest {

    @Test
    void buildCreatesCryptoCurrencyWithDefaultValues() {
        CryptoCurrency cryptoCurrency = new CryptoCurrencyBuilder().build();

        assertNotNull(cryptoCurrency);
        assertEquals(CryptoSymbol.BTCUSDT, cryptoCurrency.getSymbol());
        assertEquals(BigDecimal.valueOf(50000.0), cryptoCurrency.getPrice());
        assertEquals(LocalTime.of(12, 0), cryptoCurrency.getLastUpdateDateAndTime());
    }

    @Test
    void buildCreatesCryptoCurrencyWithCustomValues() {
        CryptoCurrency cryptoCurrency = new CryptoCurrencyBuilder()
                .withSymbol(CryptoSymbol.ETHUSDT)
                .withPrice(new BigDecimal("3000.00")) // Use BigDecimal instead of float
                .withLastUpdateDateAndTime(LocalTime.of(12, 0))
                .build();

        assertNotNull(cryptoCurrency);
        assertEquals(CryptoSymbol.ETHUSDT, cryptoCurrency.getSymbol());
        assertEquals(0, new BigDecimal("3000.00").compareTo(cryptoCurrency.getPrice())); // Compare BigDecimals
        assertEquals(LocalTime.of(12, 0), cryptoCurrency.getLastUpdateDateAndTime());
    }

}
