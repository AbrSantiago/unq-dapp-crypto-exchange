package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.model.CryptoCurrencyList;
import ar.edu.unq.dapp_api.model.CryptoQuote;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.dapp_api.service.CryptoService;

import java.util.List;


@Tag(name = "CryptoCurrency services", description = "Manage cryptocurrencies")
@RestController
@RequestMapping("/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Operation(summary = "Get all cryptocurrency prices")
    @GetMapping("/all")
    public ResponseEntity<CryptoCurrencyList> getAllCryptoCurrencyPrices() {
        CryptoCurrencyList list = cryptoService.getAllCryptoCurrencyValues();
        return ResponseEntity.ok().body(list);
    }

    @Operation(
            summary = "Get Cryptocurrency Quotes (Last 24 Hours)",
            description = "Get price quotes for a specific cryptocurrency from the last 24 hours")
    @GetMapping("/{cryptoSymbol}")
    public ResponseEntity<Object> getLast24HoursQuotes(@PathVariable String cryptoSymbol) {
        List<CryptoQuote> quotes = cryptoService.getLast24HoursQuotes(cryptoSymbol);
        return ResponseEntity.ok().body(quotes);
    }
}
