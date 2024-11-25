package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.repositories.OperationIntentRepository;
import ar.edu.unq.dapp_api.service.CryptoService;
import ar.edu.unq.dapp_api.service.UserService;
import ar.edu.unq.dapp_api.service.integration.DollarService;
import ar.edu.unq.dapp_api.validation.OperationIntentValidator;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.NewOperationIntentDTO;
import ar.edu.unq.dapp_api.exception.UserNotFoundException;
import ar.edu.unq.dapp_api.exception.OperationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OperationIntentServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private CryptoService cryptoService;

    @Mock
    private DollarService dollarService;

    @Mock
    private OperationIntentRepository operationIntentRepository;

    @Mock
    private OperationIntentValidator operationIntentValidator;

    @InjectMocks
    private OperationIntentServiceImpl operationIntentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializar mocks
    }

    @Test
    void createOperationIntent_Success() {
        Long userId = 1L;
        NewOperationIntentDTO newOperationIntentDTO = new NewOperationIntentDTO(
                CryptoSymbol.BTCUSDT,
                BigDecimal.valueOf(1.0),
                BigDecimal.valueOf(1.0),
                IntentionType.BUY
        );
        User mockUser = mock(User.class);
        BigDecimal mockCryptoPrice = BigDecimal.valueOf(100.0);
        BigDecimal mockDollarValue = BigDecimal.valueOf(200.0);
        OperationIntent mockOperationIntent = mock(OperationIntent.class);

        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(cryptoService.getCryptoCurrencyValue("BTCUSDT"))
                .thenReturn(new CryptoCurrency(CryptoSymbol.BTCUSDT, mockCryptoPrice, LocalTime.now()));
        when(dollarService.getDollarBuyValue()).thenReturn(mockDollarValue);
        when(operationIntentRepository.save(any(OperationIntent.class))).thenReturn(mockOperationIntent);

        // Simular el comportamiento del validador (mock)
        doNothing().when(operationIntentValidator).validate(any(NewOperationIntentDTO.class), any(BigDecimal.class));

        OperationIntent result = operationIntentService.createOperationIntent(userId, newOperationIntentDTO);

        assertNotNull(result);
        verify(operationIntentRepository).save(any(OperationIntent.class));
        verify(operationIntentValidator).validate(any(NewOperationIntentDTO.class), eq(mockCryptoPrice)); // Verificar llamada al validador
    }
}
