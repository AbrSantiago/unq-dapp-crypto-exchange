package ar.edu.unq.dapp_api.model;

import java.util.ArrayList;

public class CryptoCurrencyList {
    public ArrayList<CryptoCurrency> cryptos;

    public CryptoCurrencyList(ArrayList<CryptoCurrency> cryptos) {
        this.cryptos = cryptos;
    }

    public void addCrypto(CryptoCurrency crypto) {
        cryptos.add(crypto);
    }
}