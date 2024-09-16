package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoCurrencyTest {
    @Test
    void cryptoCurrencyBuilderCreatesInstanceCorrectly() {
        CryptoCurrency crypto = CryptoCurrency.builder()
                .symbol(CryptoSymbol.BTCUSDT)
                .price(50000.0f)
                .lastUpdateDateAndTime("2023-10-01T10:00:00")
                .build();

        assertNotNull(crypto);
        assertEquals(CryptoSymbol.BTCUSDT, crypto.getSymbol());
        assertEquals(50000.0f, crypto.getPrice());
        assertEquals("2023-10-01T10:00:00", crypto.getLastUpdateDateAndTime());
    }

    @Test
    void cryptoCurrencyToStringReturnsCorrectFormat() {
        CryptoCurrency crypto = new CryptoCurrency(CryptoSymbol.ETHUSDT, 3000.0f, "2023-10-01T10:00:00");

        String expected = "CryptoCurrency [symbol=ETHUSDT, price=3000.0, lastUpdateDateAndTime=2023-10-01T10:00:00]";
        assertEquals(expected, crypto.toString());
    }

    @Test
    void cryptoCurrencySettersUpdateValuesCorrectly() {
        CryptoCurrency crypto = new CryptoCurrency();
        crypto.setSymbol(CryptoSymbol.ADAUSDT);
        crypto.setPrice(2.5f);
        crypto.setLastUpdateDateAndTime("2023-10-01T10:00:00");

        assertEquals(CryptoSymbol.ADAUSDT, crypto.getSymbol());
        assertEquals(2.5f, crypto.getPrice());
        assertEquals("2023-10-01T10:00:00", crypto.getLastUpdateDateAndTime());
    }
}
