package ar.edu.unq.dapp_api.model.builders;

import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoCurrencyBuilderTest {

    @Test
    void buildCreatesCryptoCurrencyWithDefaultValues() {
        CryptoCurrency cryptoCurrency = new CryptoCurrencyBuilder().build();

        assertNotNull(cryptoCurrency);
        assertEquals(CryptoSymbol.BTCUSDT, cryptoCurrency.getSymbol());
        assertEquals(50000.0f, cryptoCurrency.getPrice());
        assertEquals("2023-10-01T10:00:00", cryptoCurrency.getLastUpdateDateAndTime());
    }

    @Test
    void buildCreatesCryptoCurrencyWithCustomValues() {
        CryptoCurrency cryptoCurrency = new CryptoCurrencyBuilder()
                .withSymbol(CryptoSymbol.ETHUSDT)
                .withPrice(3000.0f)
                .withLastUpdateDateAndTime("2023-10-02T12:00:00")
                .build();

        assertNotNull(cryptoCurrency);
        assertEquals(CryptoSymbol.ETHUSDT, cryptoCurrency.getSymbol());
        assertEquals(3000.0f, cryptoCurrency.getPrice());
        assertEquals("2023-10-02T12:00:00", cryptoCurrency.getLastUpdateDateAndTime());
    }

}
