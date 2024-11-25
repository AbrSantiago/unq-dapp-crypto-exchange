package ar.edu.unq.dapp_api.model;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CryptoCurrencyList implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<CryptoCurrency> cryptos;

    public CryptoCurrencyList() {
        this.cryptos = new ArrayList<>();
    }

    public void addCrypto(CryptoCurrency crypto) {
        cryptos.add(crypto);
    }

}