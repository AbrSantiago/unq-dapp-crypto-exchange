package ar.edu.unq.dapp_api.service.integration;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ar.edu.unq.dapp_api.model.CryptoCurrency;

import java.util.List;

@Service
public class BinanceProxyService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${integration.binance.api.url:NONE}")
    private String binanceApiURL;

    public CryptoCurrency getCryptoCurrencyValue(String symbol) {
        return restTemplate.getForObject(binanceApiURL + "ticker/price?symbol=" + symbol, CryptoCurrency.class);
    }
}