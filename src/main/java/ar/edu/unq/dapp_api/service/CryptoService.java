package ar.edu.unq.dapp_api.service;

import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.CryptoCurrencyList;

public interface CryptoService {

    CryptoCurrencyList getAllCryptoCurrencyValues();

    CryptoCurrency getCryptoCurrencyValue(String symbol);

}
