package ar.edu.unq.dapp_api.service.integration;

import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.CryptoCurrencyList;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BinanceProxyService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${integration.binance.api.url:NONE}")
    private String binanceApiURL;

    public CryptoCurrency getCryptoCurrencyValue(String symbol) {
        return restTemplate.getForObject(binanceApiURL + "ticker/price?symbol=" + symbol, CryptoCurrency.class);
    }

    public CryptoCurrencyList getAllCryptoCurrencyValue() {
        List<String> cryptos = Stream.of(CryptoSymbol.values()).map(Enum::name).toList();
        String cryptoSymbols = cryptos.stream()
                .map(symbol -> "\"" + symbol + "\"")
                .collect(Collectors.joining(",", "[", "]"));

        CryptoCurrency[] cryptoArray = restTemplate.getForObject(binanceApiURL + "ticker/price?symbols=" + cryptoSymbols, CryptoCurrency[].class);

        CryptoCurrencyList cryptoList = new CryptoCurrencyList();
        Optional.ofNullable(cryptoArray)
                .ifPresent(array -> Stream.of(array).forEach(cryptoList::addCrypto));

        return cryptoList;
    }




}