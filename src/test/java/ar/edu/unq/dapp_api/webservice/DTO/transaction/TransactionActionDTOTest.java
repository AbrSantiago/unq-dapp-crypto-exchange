package ar.edu.unq.dapp_api.webservice.DTO.transaction;

import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import ar.edu.unq.dapp_api.webservice.dto.transaction.TransactionActionDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionActionDTOTest {

    @Test
    void testTransactionActionDTOConstructor() {
        // Given
        Long userId = 1L;
        TransactionStatus action = TransactionStatus.CONFIRMED;

        // When
        TransactionActionDTO dto = new TransactionActionDTO();
        dto.setUserId(userId);
        dto.setAction(action);

        // Then
        assertNotNull(dto);
        assertEquals(userId, dto.getUserId());
        assertEquals(action, dto.getAction());
    }

    @Test
    void testTransactionActionDTODefaultConstructor() {
        // When
        TransactionActionDTO dto = new TransactionActionDTO();

        // Then
        assertNotNull(dto);
        assertNull(dto.getUserId());
        assertNull(dto.getAction());
    }
}

