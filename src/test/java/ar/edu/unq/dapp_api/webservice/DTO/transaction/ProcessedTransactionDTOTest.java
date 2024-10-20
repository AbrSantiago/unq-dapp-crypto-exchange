package ar.edu.unq.dapp_api.webservice.DTO.transaction;

import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.webservice.dto.transaction.ProcessedTransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProcessedTransactionDTOTest {

    private Transaction transaction;
    private User user;
    private OperationIntent operationIntent;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        operationIntent = mock(OperationIntent.class);
        transaction = mock(Transaction.class);
    }

    @Test
    void testFromModelValidInput() {
        // Given
        when(user.getName()).thenReturn("John");
        when(user.getSurname()).thenReturn("Doe");
        when(user.getOperationsPerformed()).thenReturn(10);
        when(user.getPointsObtained()).thenReturn(100);

        when(operationIntent.getSymbol()).thenReturn(CryptoSymbol.BTCUSDT);
        when(operationIntent.getCryptoAmount()).thenReturn(new BigDecimal("0.5"));
        when(operationIntent.getCryptoPrice()).thenReturn(new BigDecimal("60000"));

        when(transaction.getId()).thenReturn(1L);
        when(transaction.getOperationIntent()).thenReturn(operationIntent);
        when(transaction.getDirection()).thenReturn("SEND");
        when(transaction.getStatus()).thenReturn(TransactionStatus.CONFIRMED);

        // When
        ProcessedTransactionDTO dto = ProcessedTransactionDTO.fromModel(transaction, user);

        // Then
        assertNotNull(dto);
        assertEquals(transaction.getId(), dto.getId());
        assertEquals(operationIntent.getSymbol(), dto.getSymbol());
        assertEquals(operationIntent.getCryptoAmount(), dto.getCryptoAmount());
        assertEquals(operationIntent.getCryptoPrice(), dto.getCryptoPrice());
        assertEquals(user.getName(), dto.getUserName());
        assertEquals(user.getSurname(), dto.getUserSurname());
        assertEquals(user.getOperationsPerformed(), dto.getUserAmountOfOperations());
        assertEquals(user.getPointsObtained(), dto.getUserReputation());
        assertEquals(transaction.getDirection(), dto.getSendDirection());
        assertEquals(transaction.getStatus(), dto.getAction());
    }

    @Test
    void testFromModelNullTransaction() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProcessedTransactionDTO.fromModel(null, user);
        });
        assertEquals("Transaction and User cannot be null", exception.getMessage());
    }

    @Test
    void testFromModelNullUser() {
        // Given
        when(transaction.getId()).thenReturn(1L);
        when(transaction.getOperationIntent()).thenReturn(operationIntent);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProcessedTransactionDTO.fromModel(transaction, null);
        });
        assertEquals("Transaction and User cannot be null", exception.getMessage());
    }

    @Test
    void testFromModelNullTransactionAndUser() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProcessedTransactionDTO.fromModel(null, null);
        });
        assertEquals("Transaction and User cannot be null", exception.getMessage());
    }
}
