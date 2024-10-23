package ar.edu.unq.dapp_api.webservice.DTO.operation_intent;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.ActiveOperationIntentDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.UserAccountDTO;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActiveOperationIntentDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        LocalDateTime dateTime = LocalDateTime.now();
        CryptoSymbol symbol = CryptoSymbol.BTCUSDT;
        BigDecimal cryptoAmount = BigDecimal.valueOf(1.5);
        BigDecimal cryptoPrice = BigDecimal.valueOf(30000);
        BigDecimal operationARSAmount = BigDecimal.valueOf(45000);
        UserAccountDTO userAccountDTO = new UserAccountDTO("John", "Doe", 10, 5);

        // Act
        ActiveOperationIntentDTO dto = new ActiveOperationIntentDTO(id, dateTime, symbol, cryptoAmount, cryptoPrice, operationARSAmount, userAccountDTO);

        // Assert
        assertEquals(id, dto.getId());
        assertEquals(dateTime, dto.getDateTime());
        assertEquals(symbol, dto.getSymbol());
        assertEquals(cryptoAmount, dto.getCryptoAmount());
        assertEquals(cryptoPrice, dto.getCryptoPrice());
        assertEquals(operationARSAmount, dto.getOperationARSAmount());
        assertEquals(userAccountDTO, dto.getUser());
    }

    @Test
    void testFromModel() {
        // Arrange
        OperationIntent mockOperationIntent = mock(OperationIntent.class);
        User mockUser = mock(User.class);
        LocalDateTime dateTime = LocalDateTime.now();
        BigDecimal cryptoAmount = BigDecimal.valueOf(1.5);
        BigDecimal cryptoPrice = BigDecimal.valueOf(30000);
        BigDecimal operationARSAmount = BigDecimal.valueOf(45000);

        when(mockOperationIntent.getId()).thenReturn(1L);
        when(mockOperationIntent.getDateTime()).thenReturn(dateTime);
        when(mockOperationIntent.getSymbol()).thenReturn(CryptoSymbol.BTCUSDT);
        when(mockOperationIntent.getCryptoAmount()).thenReturn(cryptoAmount);
        when(mockOperationIntent.getCryptoPrice()).thenReturn(cryptoPrice);
        when(mockOperationIntent.getOperationARSAmount()).thenReturn(operationARSAmount);
        when(mockOperationIntent.getUser()).thenReturn(mockUser);

        when(mockUser.getName()).thenReturn("John");
        when(mockUser.getSurname()).thenReturn("Doe");
        when(mockUser.getOperationsPerformed()).thenReturn(10);
        when(mockUser.getReputation()).thenReturn(5);

        // Act
        ActiveOperationIntentDTO dto = ActiveOperationIntentDTO.fromModel(mockOperationIntent);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(dateTime, dto.getDateTime());
        assertEquals(CryptoSymbol.BTCUSDT, dto.getSymbol());
        assertEquals(cryptoAmount, dto.getCryptoAmount());
        assertEquals(cryptoPrice, dto.getCryptoPrice());
        assertEquals(operationARSAmount, dto.getOperationARSAmount());
        assertEquals("John", dto.getUser().getName());
        assertEquals("Doe", dto.getUser().getSurname());
        assertEquals(10, dto.getUser().getAmountOfOperations());
        assertEquals(5, dto.getUser().getReputation());
    }

    @Test
    void testFromModelWithNullUser() {
        // Arrange
        OperationIntent mockOperationIntent = mock(OperationIntent.class);

        when(mockOperationIntent.getUser()).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> ActiveOperationIntentDTO.fromModel(mockOperationIntent));
    }
}
