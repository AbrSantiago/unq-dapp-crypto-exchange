package ar.edu.unq.dapp_api.service;

import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.CryptoCurrencyList;
import ar.edu.unq.dapp_api.model.CryptoQuote;

import java.util.List;

public interface CryptoService {

    CryptoCurrencyList getAllCryptoCurrencyValues();

    CryptoCurrency getCryptoCurrencyValue(String symbol);

    List<CryptoQuote> getLast24HoursQuotes(String cryptoSymbol);
}
