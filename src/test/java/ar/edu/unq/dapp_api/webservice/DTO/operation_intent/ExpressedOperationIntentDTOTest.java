package ar.edu.unq.dapp_api.webservice.DTO.operation_intent;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.ExpressedOperationIntentDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpressedOperationIntentDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        CryptoSymbol symbol = CryptoSymbol.BTCUSDT;
        BigDecimal cryptoAmount = BigDecimal.valueOf(2.5);
        BigDecimal cryptoPrice = BigDecimal.valueOf(50000);
        BigDecimal operationARSAmount = BigDecimal.valueOf(125000);
        String userName = "Alice";
        String userSurname = "Johnson";
        IntentionType type = IntentionType.BUY;

        // Act
        ExpressedOperationIntentDTO dto = new ExpressedOperationIntentDTO(id, symbol, cryptoAmount, cryptoPrice, operationARSAmount, userName, userSurname, type);

        // Assert
        assertEquals(id, dto.getId());
        assertEquals(symbol, dto.getSymbol());
        assertEquals(cryptoAmount, dto.getCryptoAmount());
        assertEquals(cryptoPrice, dto.getCryptoPrice());
        assertEquals(operationARSAmount, dto.getOperationARSAmount());
        assertEquals(userName, dto.getUserName());
        assertEquals(userSurname, dto.getUserSurname());
        assertEquals(type, dto.getType());
    }

    @Test
    void testFromModel() {
        // Arrange
        OperationIntent mockOperationIntent = mock(OperationIntent.class);
        User mockUser = mock(User.class);
        BigDecimal cryptoAmount = BigDecimal.valueOf(2.5);
        BigDecimal cryptoPrice = BigDecimal.valueOf(50000);
        BigDecimal operationARSAmount = BigDecimal.valueOf(125000);

        when(mockOperationIntent.getId()).thenReturn(1L);
        when(mockOperationIntent.getSymbol()).thenReturn(CryptoSymbol.BTCUSDT);
        when(mockOperationIntent.getCryptoAmount()).thenReturn(cryptoAmount);
        when(mockOperationIntent.getCryptoPrice()).thenReturn(cryptoPrice);
        when(mockOperationIntent.getOperationARSAmount()).thenReturn(operationARSAmount);
        when(mockOperationIntent.getUser()).thenReturn(mockUser);
        when(mockOperationIntent.getType()).thenReturn(IntentionType.BUY);

        when(mockUser.getName()).thenReturn("Alice");
        when(mockUser.getSurname()).thenReturn("Johnson");

        // Act
        ExpressedOperationIntentDTO dto = ExpressedOperationIntentDTO.fromModel(mockOperationIntent);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(CryptoSymbol.BTCUSDT, dto.getSymbol());
        assertEquals(cryptoAmount, dto.getCryptoAmount());
        assertEquals(cryptoPrice, dto.getCryptoPrice());
        assertEquals(operationARSAmount, dto.getOperationARSAmount());
        assertEquals("Alice", dto.getUserName());
        assertEquals("Johnson", dto.getUserSurname());
        assertEquals(IntentionType.BUY, dto.getType());
    }

    @Test
    void testFromModelWithNullUser() {
        // Arrange
        OperationIntent mockOperationIntent = mock(OperationIntent.class);
        when(mockOperationIntent.getUser()).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> ExpressedOperationIntentDTO.fromModel(mockOperationIntent));
    }
}
