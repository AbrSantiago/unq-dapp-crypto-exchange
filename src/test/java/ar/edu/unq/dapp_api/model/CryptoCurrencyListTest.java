package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoCurrencyListTest {

    @Test
    void addCryptoAddsCryptoToList() {
        CryptoCurrencyList cryptoList = new CryptoCurrencyList();
        CryptoCurrency crypto = CryptoCurrency.builder()
                .symbol(CryptoSymbol.BTCUSDT)
                .price(50000.0f)
                .lastUpdateDateAndTime("2023-10-01T10:00:00")
                .build();

        cryptoList.addCrypto(crypto);

        assertEquals(1, cryptoList.getCryptos().size());
        assertEquals(crypto, cryptoList.getCryptos().get(0));
    }

    @Test
    void getCryptosReturnsListOfAddedCryptos() {
        CryptoCurrencyList cryptoList = new CryptoCurrencyList();
        CryptoCurrency crypto1 = CryptoCurrency.builder()
                .symbol(CryptoSymbol.BTCUSDT)
                .price(50000.0f)
                .lastUpdateDateAndTime("2023-10-01T10:00:00")
                .build();
        CryptoCurrency crypto2 = CryptoCurrency.builder()
                .symbol(CryptoSymbol.ETHUSDT)
                .price(3000.0f)
                .lastUpdateDateAndTime("2023-10-01T10:00:00")
                .build();

        cryptoList.addCrypto(crypto1);
        cryptoList.addCrypto(crypto2);

        assertEquals(2, cryptoList.getCryptos().size());
        assertTrue(cryptoList.getCryptos().contains(crypto1));
        assertTrue(cryptoList.getCryptos().contains(crypto2));
    }

    @Test
    void cryptoCurrencyListIsInitiallyEmpty() {
        CryptoCurrencyList cryptoList = new CryptoCurrencyList();

        assertTrue(cryptoList.getCryptos().isEmpty());
    }
}
