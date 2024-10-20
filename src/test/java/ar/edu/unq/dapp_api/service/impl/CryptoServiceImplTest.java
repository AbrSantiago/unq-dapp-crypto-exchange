package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.CryptoCurrencyList;
import ar.edu.unq.dapp_api.repositories.CryptoRepository;
import ar.edu.unq.dapp_api.service.integration.BinanceProxyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CryptoServiceImplTest {

    @Mock
    private CryptoRepository cryptoRepository;

    @Mock
    private BinanceProxyService binanceProxyService;

    @InjectMocks
    private CryptoServiceImpl cryptoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCryptoCurrencyValues_Success() {
        CryptoCurrencyList mockCryptoList = mock(CryptoCurrencyList.class);
        List<CryptoCurrency> mockCryptos = List.of(mock(CryptoCurrency.class));
        when(mockCryptoList.getCryptos()).thenReturn(mockCryptos);
        when(binanceProxyService.getAllCryptoCurrencyValue()).thenReturn(mockCryptoList);

        CryptoCurrencyList result = cryptoService.getAllCryptoCurrencyValues();

        assertNotNull(result);
        verify(cryptoRepository).saveAll(mockCryptos);
    }

    @Test
    void getCryptoCurrencyValue_Success() {
        String symbol = "BTCUSDT";
        CryptoCurrency mockCrypto = mock(CryptoCurrency.class);
        when(binanceProxyService.getCryptoCurrencyValue(symbol)).thenReturn(mockCrypto);

        CryptoCurrency result = cryptoService.getCryptoCurrencyValue(symbol);

        assertNotNull(result);
        verify(cryptoRepository).save(mockCrypto);
    }

    @Test
    void getCryptoCurrencyValue_InvalidSymbol() {
        String symbol = "INVALID";
        when(binanceProxyService.getCryptoCurrencyValue(symbol)).thenReturn(null);

        CryptoCurrency result = cryptoService.getCryptoCurrencyValue(symbol);

        assertNull(result);
        verify(cryptoRepository, never()).save(any(CryptoCurrency.class));
    }
}