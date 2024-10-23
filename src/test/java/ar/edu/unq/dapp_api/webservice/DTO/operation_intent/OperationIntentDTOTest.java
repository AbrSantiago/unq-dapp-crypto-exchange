package ar.edu.unq.dapp_api.webservice.DTO.operation_intent;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.OperationIntentDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OperationIntentDTOTest {

    @Test
    void testFromModel() {
        // Given
        OperationIntent operationIntent = mock(OperationIntent.class);
        Transaction transaction = mock(Transaction.class);
        User user = mock(User.class);
        LocalDateTime dateTime = LocalDateTime.now();

        when(operationIntent.getId()).thenReturn(1L);
        when(operationIntent.getSymbol()).thenReturn(CryptoSymbol.BTCUSDT);
        when(operationIntent.getCryptoAmount()).thenReturn(BigDecimal.valueOf(0.5));
        when(operationIntent.getCryptoPrice()).thenReturn(BigDecimal.valueOf(50000));
        when(operationIntent.getOperationARSAmount()).thenReturn(BigDecimal.valueOf(2500000));
        when(operationIntent.getType()).thenReturn(IntentionType.BUY);
        when(operationIntent.getDateTime()).thenReturn(dateTime);
        when(operationIntent.getStatus()).thenReturn(OperationStatus.OPEN);
        when(operationIntent.getTransaction()).thenReturn(transaction);
        when(transaction.getId()).thenReturn(100L);
        when(operationIntent.getUser()).thenReturn(user);
        when(user.getName()).thenReturn("John");
        when(user.getSurname()).thenReturn("Doe");

        // When
        OperationIntentDTO dto = OperationIntentDTO.fromModel(operationIntent);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals(CryptoSymbol.BTCUSDT, dto.getSymbol());
        assertEquals(BigDecimal.valueOf(0.5), dto.getCryptoAmount());
        assertEquals(BigDecimal.valueOf(50000), dto.getCryptoPrice());
        assertEquals(BigDecimal.valueOf(2500000), dto.getOperationARSAmount());
        assertEquals(IntentionType.BUY, dto.getType());
        assertEquals(dateTime, dto.getDateTime());
        assertEquals(OperationStatus.OPEN, dto.getStatus());
        assertEquals(100L, dto.getTransactionId());
        assertNotNull(dto.getUser());
        assertEquals("John", dto.getUser().getName());
        assertEquals("Doe", dto.getUser().getSurname());
    }

    @Test
    void testFromModelWithNullTransaction() {
        // Given
        OperationIntent operationIntent = mock(OperationIntent.class);
        User user = mock(User.class);
        LocalDateTime dateTime = LocalDateTime.now();

        when(operationIntent.getId()).thenReturn(2L);
        when(operationIntent.getSymbol()).thenReturn(CryptoSymbol.ETHUSDT);
        when(operationIntent.getCryptoAmount()).thenReturn(BigDecimal.valueOf(1));
        when(operationIntent.getCryptoPrice()).thenReturn(BigDecimal.valueOf(3500));
        when(operationIntent.getOperationARSAmount()).thenReturn(BigDecimal.valueOf(3500 * 300));
        when(operationIntent.getType()).thenReturn(IntentionType.SELL);
        when(operationIntent.getDateTime()).thenReturn(dateTime);
        when(operationIntent.getStatus()).thenReturn(OperationStatus.OPEN);
        when(operationIntent.getTransaction()).thenReturn(null);
        when(operationIntent.getUser()).thenReturn(user);
        when(user.getName()).thenReturn("Jane");
        when(user.getSurname()).thenReturn("Doe");

        // When
        OperationIntentDTO dto = OperationIntentDTO.fromModel(operationIntent);

        // Then
        assertEquals(2L, dto.getId());
        assertEquals(CryptoSymbol.ETHUSDT, dto.getSymbol());
        assertEquals(BigDecimal.valueOf(1), dto.getCryptoAmount());
        assertEquals(BigDecimal.valueOf(3500), dto.getCryptoPrice());
        assertEquals(BigDecimal.valueOf(3500 * 300), dto.getOperationARSAmount());
        assertEquals(IntentionType.SELL, dto.getType());
        assertEquals(dateTime, dto.getDateTime());
        assertEquals(OperationStatus.OPEN, dto.getStatus());
        assertNull(dto.getTransactionId());
        assertNotNull(dto.getUser());
        assertEquals("Jane", dto.getUser().getName());
        assertEquals("Doe", dto.getUser().getSurname());
    }

    @Test
    void testFromModelList() {
        // Given
        OperationIntent operationIntent1 = mock(OperationIntent.class);
        OperationIntent operationIntent2 = mock(OperationIntent.class);
        User user1 = mock(User.class);
        User user2 = mock(User.class);

        when(operationIntent1.getId()).thenReturn(1L);
        when(operationIntent1.getSymbol()).thenReturn(CryptoSymbol.BTCUSDT);
        when(operationIntent1.getCryptoAmount()).thenReturn(BigDecimal.valueOf(0.5));
        when(operationIntent1.getCryptoPrice()).thenReturn(BigDecimal.valueOf(50000));
        when(operationIntent1.getOperationARSAmount()).thenReturn(BigDecimal.valueOf(2500000));
        when(operationIntent1.getType()).thenReturn(IntentionType.BUY);
        when(operationIntent1.getDateTime()).thenReturn(LocalDateTime.now());
        when(operationIntent1.getStatus()).thenReturn(OperationStatus.OPEN);
        when(operationIntent1.getTransaction()).thenReturn(null);
        when(operationIntent1.getUser()).thenReturn(user1);
        when(user1.getName()).thenReturn("John");
        when(user1.getSurname()).thenReturn("Doe");

        when(operationIntent2.getId()).thenReturn(2L);
        when(operationIntent2.getSymbol()).thenReturn(CryptoSymbol.ETHUSDT);
        when(operationIntent2.getCryptoAmount()).thenReturn(BigDecimal.valueOf(1));
        when(operationIntent2.getCryptoPrice()).thenReturn(BigDecimal.valueOf(3500));
        when(operationIntent2.getOperationARSAmount()).thenReturn(BigDecimal.valueOf(3500 * 300));
        when(operationIntent2.getType()).thenReturn(IntentionType.SELL);
        when(operationIntent2.getDateTime()).thenReturn(LocalDateTime.now());
        when(operationIntent2.getStatus()).thenReturn(OperationStatus.OPEN);
        when(operationIntent2.getTransaction()).thenReturn(null);
        when(operationIntent2.getUser()).thenReturn(user2);
        when(user2.getName()).thenReturn("Jane");
        when(user2.getSurname()).thenReturn("Doe");

        List<OperationIntent> operationIntents = List.of(operationIntent1, operationIntent2);

        // When
        List<OperationIntentDTO> dtos = OperationIntentDTO.fromModelList(operationIntents);

        // Then
        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getId());
        assertEquals(2L, dtos.get(1).getId());
    }
}

