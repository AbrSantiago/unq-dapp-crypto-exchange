package ar.edu.unq.dapp_api.webservice.DTO.transaction;

import ar.edu.unq.dapp_api.webservice.dto.transaction.NewTransactionDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NewTransactionDTOTest {

    @Test
    void testNewTransactionDTOConstructor() {
        // Given
        Long userId = 1L;
        Long operationIntentId = 2L;

        // When
        NewTransactionDTO dto = new NewTransactionDTO(userId, operationIntentId);

        // Then
        assertEquals(userId, dto.getUserId());
        assertEquals(operationIntentId, dto.getOperationIntentId());
    }

    @Test
    void testNewTransactionDTODefaultConstructor() {
        // Given
        NewTransactionDTO dto = new NewTransactionDTO(null, null);

        // Then
        assertNull(dto.getUserId());
        assertNull(dto.getOperationIntentId());
    }
}
