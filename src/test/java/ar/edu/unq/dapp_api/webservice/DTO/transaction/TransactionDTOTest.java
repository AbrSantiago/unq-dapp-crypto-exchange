package ar.edu.unq.dapp_api.webservice.DTO.transaction;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.OperationIntentDTO;
import ar.edu.unq.dapp_api.webservice.dto.transaction.TransactionDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.SimpleUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionDTOTest {

    private Transaction transactionMock;
    private OperationIntentDTO operationIntentDTO;
    private SimpleUserDTO sellerDTO;
    private SimpleUserDTO buyerDTO;

    @BeforeEach
    void setUp() {
        // Create mock objects
        transactionMock = mock(Transaction.class);
        operationIntentDTO = mock(OperationIntentDTO.class);
        sellerDTO = mock(SimpleUserDTO.class);
        buyerDTO = mock(SimpleUserDTO.class);
    }

    @Test
    void testTransactionDTOConstructor() {
        // Given
        Long id = 1L;
        TransactionStatus status = TransactionStatus.CONFIRMED;
        LocalDateTime startTime = LocalDateTime.now();

        // When
        TransactionDTO dto = new TransactionDTO(id, operationIntentDTO, sellerDTO, buyerDTO, status, startTime);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(operationIntentDTO, dto.getOperationIntent());
        assertEquals(sellerDTO, dto.getSeller());
        assertEquals(buyerDTO, dto.getBuyer());
        assertEquals(status, dto.getStatus());
        assertEquals(startTime, dto.getStartTime());
    }

    @Test
    void fromModelCreatesDTOCorrectly() {
        // Given
        Long id = 1L;
        LocalDateTime startTime = LocalDateTime.now();
        TransactionStatus status = TransactionStatus.PENDING;
        OperationIntent operationIntent = mock(OperationIntent.class);
        User seller = mock(User.class);
        User buyer = mock(User.class);

        // Mock behavior
        when(transactionMock.getId()).thenReturn(id);
        when(transactionMock.getOperationIntent()).thenReturn(operationIntent);
        when(transactionMock.getSeller()).thenReturn(seller);
        when(transactionMock.getBuyer()).thenReturn(buyer);
        when(transactionMock.getStatus()).thenReturn(status);
        when(transactionMock.getStartTime()).thenReturn(startTime);

        // Ensure user is not null
        when(operationIntent.getUser()).thenReturn(mock(User.class));
        when(operationIntent.getUser().getName()).thenReturn("John");

        // When
        TransactionDTO dto = TransactionDTO.fromModel(transactionMock);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(operationIntent.getId(), dto.getOperationIntent().getId());
        assertEquals(seller.getName(), dto.getSeller().getName());
        assertEquals(buyer.getName(), dto.getBuyer().getName());
        assertEquals(status, dto.getStatus());
        assertEquals(startTime, dto.getStartTime());
    }
}

