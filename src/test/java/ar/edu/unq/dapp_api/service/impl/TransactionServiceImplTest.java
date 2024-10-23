package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.exception.*;
import ar.edu.unq.dapp_api.model.CryptoCurrency;
import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import ar.edu.unq.dapp_api.repositories.TransactionRepository;
import ar.edu.unq.dapp_api.service.CryptoService;
import ar.edu.unq.dapp_api.service.OperationIntentService;
import ar.edu.unq.dapp_api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private UserService userService;
    @Mock
    private OperationIntentService operationIntentService;
    @Mock
    private CryptoService cryptoService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private User mockUser;
    private OperationIntent mockOperationIntent;
    private Transaction mockTransaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = mock(User.class);
        mockOperationIntent = mock(OperationIntent.class);
        mockTransaction = mock(Transaction.class);
    }

    @Test
    void testCreateTransaction_UserDoesNotExist() {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> transactionService.createTransaction(userId, 1L));
    }

    @Test
    void testCreateTransaction_OperationDoesNotExist() {
        Long userId = 1L;
        Long operationIntentId = 1L;
        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(operationIntentService.getOperationIntentById(operationIntentId)).thenReturn(null);

        assertThrows(OperationNotFoundException.class, () -> transactionService.createTransaction(userId, operationIntentId));
    }

    @Test
    void testCreateTransaction_Success() {
        Long userId = 1L;
        Long operationIntentId = 1L;
        BigDecimal mockPrice = BigDecimal.valueOf(100.0);

        // Mocking behavior for user, operation intent, and crypto price
        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(operationIntentService.getOperationIntentById(operationIntentId)).thenReturn(mockOperationIntent);
        when(cryptoService.getCryptoCurrencyValue(anyString())).thenReturn(new CryptoCurrency(CryptoSymbol.BTCUSDT, mockPrice, LocalTime.now()));
        when(mockOperationIntent.getSymbol()).thenReturn(CryptoSymbol.BTCUSDT);
        when(mockOperationIntent.generateTransaction(eq(mockUser), any(BigDecimal.class))).thenReturn(mockTransaction);
        when(mockOperationIntent.getStatus()).thenReturn(OperationStatus.OPEN);

        // Mocking transaction repository save
        when(transactionRepository.save(mockTransaction)).thenReturn(mockTransaction);

        // Call the method under test
        Transaction transaction = transactionService.createTransaction(userId, operationIntentId);

        // Verifications
        verify(transactionRepository).save(mockTransaction);
        assertNotNull(transaction);  // Ensure that the transaction is not null
        assertEquals(mockTransaction, transaction);  // Ensure that returned transaction matches the mock
    }

    @Test
    void testProcessTransaction_TransactionDoesNotExist() {
        Long transactionId = 1L;
        Long userId = 1L;
        TransactionStatus action = TransactionStatus.CONFIRMED;

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> transactionService.processTransaction(transactionId, userId, action));
    }

    @Test
    void testProcessTransaction_InvalidAction() {
        Long transactionId = 1L;
        Long userId = 1L;
        TransactionStatus invalidAction = TransactionStatus.PENDING;

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(mockTransaction));
        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(mockTransaction.getSeller()).thenReturn(mockUser);
        when(mockTransaction.getBuyer()).thenReturn(mockUser);
        when(mockTransaction.getStatus()).thenReturn(TransactionStatus.PENDING);

        assertThrows(InvalidActionException.class, () -> transactionService.processTransaction(transactionId, userId, invalidAction));
    }

    @Test
    void testProcessTransaction_Transferred() {
        Long transactionId = 1L;
        Long userId = 1L;
        TransactionStatus action = TransactionStatus.TRANSFERRED;

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(mockTransaction));
        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(mockTransaction.getSeller()).thenReturn(mockUser);
        when(mockTransaction.getBuyer()).thenReturn(mockUser);
        when(mockTransaction.getStatus()).thenReturn(TransactionStatus.TRANSFERRED);

        Transaction result = transactionService.processTransaction(transactionId, userId, action);

        verify(mockUser).notifySentTransaction(mockTransaction);
        verify(transactionRepository).save(mockTransaction);
        assertEquals(mockTransaction, result);
    }

    @Test
    void testProcessTransaction_Confirmed() {
        Long transactionId = 1L;
        Long userId = 1L;
        TransactionStatus action = TransactionStatus.CONFIRMED;

        OperationIntent mockOperationIntent = mock(OperationIntent.class);
        when(mockOperationIntent.getUser()).thenReturn(mockUser);

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(mockTransaction));
        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(mockTransaction.getSeller()).thenReturn(mockUser);
        when(mockTransaction.getBuyer()).thenReturn(mockUser);
        when(mockTransaction.getOperationIntent()).thenReturn(mockOperationIntent);
        when(mockTransaction.getStatus()).thenReturn(TransactionStatus.PENDING);

        Transaction result = transactionService.processTransaction(transactionId, userId, action);

        verify(mockUser).notifyReceivedTransaction(mockTransaction);
        verify(transactionRepository).save(mockTransaction);
        assertEquals(mockTransaction, result);
    }

    @Test
    void testProcessTransaction_Cancelled() {
        Long transactionId = 1L;
        Long userId = 1L;
        TransactionStatus action = TransactionStatus.CANCELLED;

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(mockTransaction));
        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(mockTransaction.getSeller()).thenReturn(mockUser);
        when(mockTransaction.getBuyer()).thenReturn(mockUser);
        when(mockTransaction.getStatus()).thenReturn(TransactionStatus.PENDING);

        Transaction result = transactionService.processTransaction(transactionId, userId, action);

        verify(mockUser).cancelTransaction(mockTransaction);
        verify(transactionRepository).save(mockTransaction);
        assertEquals(mockTransaction, result);
    }

    @Test
    void testGetConfirmedTransactionsByDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();

        when(transactionRepository.findConfirmedTransactionsByDateRange(startDate, endDate)).thenReturn(10);

        int confirmedCount = transactionService.getConfirmedTransactionsByDateRange(startDate, endDate);

        assertEquals(10, confirmedCount);
    }
}
