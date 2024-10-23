package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CryptoCurrencyTest {

    @Test
    void cryptoCurrencyBuilderCreatesInstanceCorrectly() {
        CryptoCurrency crypto = CryptoCurrency.builder()
                .symbol(CryptoSymbol.BTCUSDT)
                .price(new BigDecimal("50000.00")) // Use BigDecimal
                .lastUpdateDateAndTime(LocalTime.of(12, 0))
                .build();

        assertNotNull(crypto);
        assertEquals(CryptoSymbol.BTCUSDT, crypto.getSymbol());
        assertEquals(0, new BigDecimal("50000.00").compareTo(crypto.getPrice())); // Compare BigDecimals
        assertEquals(LocalTime.of(12, 0), crypto.getLastUpdateDateAndTime());
    }

    @Test
    void cryptoCurrencyToStringReturnsCorrectFormat() {
        CryptoCurrency crypto = new CryptoCurrency(CryptoSymbol.ETHUSDT, new BigDecimal("3000.00"), LocalTime.of(12, 0));

        String expected = "CryptoCurrency [symbol=ETHUSDT, price=3000.00, lastUpdateDateAndTime=12:00]";
        assertEquals(expected, crypto.toString());
    }

    @Test
    void cryptoCurrencySettersUpdateValuesCorrectly() {
        CryptoCurrency crypto = new CryptoCurrency();
        crypto.setSymbol(CryptoSymbol.ADAUSDT);
        crypto.setPrice(new BigDecimal("2.50")); // Use BigDecimal
        crypto.setLastUpdateDateAndTime(LocalTime.of(12, 0));

        assertEquals(CryptoSymbol.ADAUSDT, crypto.getSymbol());
        assertEquals(0, new BigDecimal("2.50").compareTo(crypto.getPrice())); // Compare BigDecimals
        assertEquals(LocalTime.of(12, 0), crypto.getLastUpdateDateAndTime());
    }
}
