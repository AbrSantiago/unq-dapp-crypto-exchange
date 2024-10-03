package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.CryptoCurrencyList;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.repositories.CryptoRepository;
import ar.edu.unq.dapp_api.service.CryptoService;
import ar.edu.unq.dapp_api.service.integration.BinanceProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class CryptoServiceImpl implements CryptoService {

    private final CryptoRepository cryptoRepository;

    private final BinanceProxyService binanceProxyService;

    @Autowired
    public CryptoServiceImpl(CryptoRepository cryptoRepository, BinanceProxyService binanceProxyService) {
        this.binanceProxyService = binanceProxyService;
        this.cryptoRepository = cryptoRepository;
    }

    @Override
    public CryptoCurrencyList getAllCryptoCurrencyValues() {
        CryptoCurrencyList list = new CryptoCurrencyList();
        for (CryptoSymbol crypto : CryptoSymbol.values()) {
            CryptoCurrency entity = binanceProxyService.getCryptoCurrencyValue(crypto.name());

            if (entity != null) {
                entity.setLastUpdateDateAndTime(LocalTime.now());
            }
            list.addCrypto(entity);
        }
        cryptoRepository.saveAll(list.getCryptos());
        return list;
    }

    @Override
    public CryptoCurrency getCryptoCurrencyValue(String symbol) {
        return null;
    }
}
