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

    @InjectMocks
    private OperationIntentServiceImpl operationIntentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOperationIntent_Success() {
        Long userId = 1L;
        NewOperationIntentDTO newOperationIntentDTO = new NewOperationIntentDTO(CryptoSymbol.BTCUSDT, BigDecimal.valueOf(1.0), IntentionType.BUY);
        User mockUser = mock(User.class);
        BigDecimal mockCryptoPrice = BigDecimal.valueOf(100.0);
        BigDecimal mockDollarValue = BigDecimal.valueOf(200.0);
        OperationIntent mockOperationIntent = mock(OperationIntent.class);

        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(cryptoService.getCryptoCurrencyValue("BTCUSDT")).thenReturn(new CryptoCurrency(CryptoSymbol.BTCUSDT, mockCryptoPrice, LocalTime.now()));
        when(dollarService.getDollarBuyValue()).thenReturn(mockDollarValue);
        when(operationIntentRepository.save(any(OperationIntent.class))).thenReturn(mockOperationIntent);

        OperationIntent result = operationIntentService.createOperationIntent(userId, newOperationIntentDTO);

        assertNotNull(result);
        verify(operationIntentRepository).save(any(OperationIntent.class));
    }

    @Test
    void createOperationIntent_UserDoesNotExist() {
        Long userId = 1L;
        NewOperationIntentDTO newOperationIntentDTO = new NewOperationIntentDTO(CryptoSymbol.BTCUSDT, BigDecimal.valueOf(1.0),IntentionType.BUY);

        when(userService.getUserById(userId)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> operationIntentService.createOperationIntent(userId, newOperationIntentDTO));
    }

    @Test
    void getActivesOperationIntents_Success() {
        List<OperationIntent> mockOperationIntents = List.of(mock(OperationIntent.class));

        when(operationIntentRepository.findActivesOperationIntents(OperationStatus.OPEN)).thenReturn(mockOperationIntents);

        List<OperationIntent> result = operationIntentService.getActivesOperationIntents();

        assertNotNull(result);
        assertEquals(mockOperationIntents.size(), result.size());
    }

    @Test
    void getOperationIntentById_Success() {
        Long operationIntentId = 1L;
        OperationIntent mockOperationIntent = mock(OperationIntent.class);

        when(operationIntentRepository.findById(operationIntentId)).thenReturn(Optional.of(mockOperationIntent));

        OperationIntent result = operationIntentService.getOperationIntentById(operationIntentId);

        assertNotNull(result);
        assertEquals(mockOperationIntent, result);
    }

    @Test
    void getOperationIntentById_OperationDoesNotExist() {
        Long operationIntentId = 1L;

        when(operationIntentRepository.findById(operationIntentId)).thenReturn(Optional.empty());

        assertThrows(OperationNotFoundException.class, () -> operationIntentService.getOperationIntentById(operationIntentId));
    }

    @Test
    void getActivesOperationIntentsFromUser_Success() {
        Long userId = 1L;
        List<OperationIntent> mockOperationIntents = List.of(mock(OperationIntent.class));

        when(userService.getUserById(userId)).thenReturn(mock(User.class));
        when(operationIntentRepository.findActivesOperationIntentsFromUser(userId, OperationStatus.OPEN)).thenReturn(mockOperationIntents);

        List<OperationIntent> result = operationIntentService.getActivesOperationIntentsFromUser(userId);

        assertNotNull(result);
        assertEquals(mockOperationIntents.size(), result.size());
    }

    @Test
    void getActivesOperationIntentsFromUser_UserDoesNotExist() {
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> operationIntentService.getActivesOperationIntentsFromUser(userId));
    }
}