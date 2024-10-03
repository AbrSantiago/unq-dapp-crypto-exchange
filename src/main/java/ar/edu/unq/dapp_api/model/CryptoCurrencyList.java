package ar.edu.unq.dapp_api.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CryptoCurrencyList {
    private final List<CryptoCurrency> cryptos;

    public CryptoCurrencyList(List<CryptoCurrency> cryptoCurrencies) {
        this.cryptos = new ArrayList<>();
    }

    public CryptoCurrencyList() {
        this.cryptos = new ArrayList<>();
    }

    public void addCrypto(CryptoCurrency crypto) {
        cryptos.add(crypto);
    }

}