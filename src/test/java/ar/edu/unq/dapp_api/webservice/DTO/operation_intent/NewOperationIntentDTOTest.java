package ar.edu.unq.dapp_api.webservice.DTO.operation_intent;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.NewOperationIntentDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class NewOperationIntentDTOTest {

    @Test
    void testConstructorWithArguments() {
        // Given
        CryptoSymbol symbol = CryptoSymbol.BTCUSDT;
        BigDecimal cryptoAmount = BigDecimal.valueOf(0.5);
        BigDecimal operationARSAmount = BigDecimal.valueOf(0.5);
        IntentionType type = IntentionType.BUY;

        // When
        NewOperationIntentDTO dto = new NewOperationIntentDTO(symbol, cryptoAmount, operationARSAmount,type);

        // Then
        assertEquals(symbol, dto.getSymbol());
        assertEquals(cryptoAmount, dto.getCryptoAmount());
        assertEquals(type, dto.getType());
    }

    @Test
    void testEmptyConstructor() {
        // When
        NewOperationIntentDTO dto = new NewOperationIntentDTO();

        // Then
        assertNull(dto.getSymbol());
        assertNull(dto.getCryptoAmount());
        assertNull(dto.getType());
    }

    @Test
    void testSetAndGetMethods() {
        // Given
        CryptoSymbol symbol = CryptoSymbol.BTCUSDT;
        BigDecimal cryptoAmount = BigDecimal.valueOf(1.2);
        BigDecimal operationARSAmount = BigDecimal.valueOf(0.5);
        IntentionType type = IntentionType.SELL;

        // When
        NewOperationIntentDTO dto = new NewOperationIntentDTO(symbol, cryptoAmount, operationARSAmount, type);

        // Then
        assertEquals(CryptoSymbol.BTCUSDT, dto.getSymbol());
        assertEquals(BigDecimal.valueOf(1.2), dto.getCryptoAmount());
        assertEquals(IntentionType.SELL, dto.getType());
    }
}

