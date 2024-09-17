package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.builders.CryptoCurrencyBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoCurrencyListTest {

    @Test
    void addCryptoAddsCryptoToList() {
        CryptoCurrencyList cryptoList = new CryptoCurrencyList();
        CryptoCurrency crypto = new CryptoCurrencyBuilder().build();

        cryptoList.addCrypto(crypto);

        assertEquals(1, cryptoList.getCryptos().size());
        assertEquals(crypto, cryptoList.getCryptos().getFirst());
    }

    @Test
    void getCryptosReturnsListOfAddedCryptos() {
        CryptoCurrencyList cryptoList = new CryptoCurrencyList();
        CryptoCurrency crypto1 = new CryptoCurrencyBuilder().build();
        CryptoCurrency crypto2 = new CryptoCurrencyBuilder().build();

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
