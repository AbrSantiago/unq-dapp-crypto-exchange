package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.CryptoCurrencyList;
import ar.edu.unq.dapp_api.model.CryptoQuote;
import ar.edu.unq.dapp_api.repositories.CryptoRepository;
import ar.edu.unq.dapp_api.service.CryptoService;
import ar.edu.unq.dapp_api.service.integration.BinanceProxyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class CryptoServiceImpl implements CryptoService {

    private final CryptoRepository cryptoRepository;

    private final BinanceProxyService binanceProxyService;

    @Autowired
    public CryptoServiceImpl(CryptoRepository cryptoRepository, BinanceProxyService binanceProxyService) {
        this.binanceProxyService = binanceProxyService;
        this.cryptoRepository = cryptoRepository;
    }

    @Override
    @Cacheable("cryptos")
    public CryptoCurrencyList getAllCryptoCurrencyValues() {
        CryptoCurrencyList cryptoList = binanceProxyService.getAllCryptoCurrencyValue();

        for(CryptoCurrency crypto : cryptoList.getCryptos()) {
            crypto.setLastUpdateDateAndTime(LocalTime.now());
        }

        cryptoRepository.saveAll(cryptoList.getCryptos());
        return cryptoList;
    }

    @Override
    public CryptoCurrency getCryptoCurrencyValue(String symbol) {
        CryptoCurrency crypto = binanceProxyService.getCryptoCurrencyValue(symbol);
        if (crypto != null) {
            crypto.setLastUpdateDateAndTime(LocalTime.now());
            cryptoRepository.save(crypto);
        }
        return crypto;
    }

    @Override
    public List<CryptoQuote> getLast24HoursQuotes(String cryptoSymbol) {
        return binanceProxyService.getLast24HoursQuotes(cryptoSymbol);
    }
}
