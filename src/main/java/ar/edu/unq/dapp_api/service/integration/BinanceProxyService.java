package ar.edu.unq.dapp_api.service.integration;

import ar.edu.unq.dapp_api.exception.CryptoCurrencyNotFoundException;
import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.CryptoCurrencyList;
import ar.edu.unq.dapp_api.model.CryptoQuote;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BinanceProxyService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${integration.binance.api.url:NONE}")
    String binanceApiURL;

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

    public List<CryptoQuote> getLast24HoursQuotes(String cryptoSymbol) {
        String url = binanceApiURL + "klines?symbol=" + cryptoSymbol + "&interval=30m&limit=48";
        Object[][] response = restTemplate.getForEntity(
                url,
                Object[][].class
        ).getBody();
        if (response == null) {
            throw new CryptoCurrencyNotFoundException();
        }
        return createLast24HoursQuotesFromResponse(response);
    }

    private List<CryptoQuote> createLast24HoursQuotesFromResponse(Object[][] body) {
        List<CryptoQuote> quotes = new ArrayList<>();
        for (Object[] item : body) {
            LocalDateTime timestamp = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli((Long) item[0]),
                    ZoneId.systemDefault()
            );
            String price = (String) item[1];
            quotes.add(new CryptoQuote(timestamp, price));
        }
        return quotes;
    }
}