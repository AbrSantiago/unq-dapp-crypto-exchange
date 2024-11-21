package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.exception.*;
import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import ar.edu.unq.dapp_api.service.TransactionService;
import ar.edu.unq.dapp_api.service.UserService;
import ar.edu.unq.dapp_api.webservice.dto.transaction.NewTransactionDTO;
import ar.edu.unq.dapp_api.webservice.dto.transaction.TransactionActionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testCreateTransactionSuccessfully() throws Exception {
        Long userId = 1L;
        Long operationIntentId = 2L;

        Transaction mockTransaction = Mockito.mock(Transaction.class);
        OperationIntent mockOperationIntent = new OperationIntent();
        mockOperationIntent.setSymbol(CryptoSymbol.BTCUSDT);

        when(mockTransaction.getId()).thenReturn(1L);
        when(mockTransaction.getOperationIntent()).thenReturn(mockOperationIntent);
        when(mockTransaction.getStatus()).thenReturn(TransactionStatus.PENDING);

        when(transactionService.createTransaction(userId, operationIntentId))
                .thenReturn(mockTransaction);

        NewTransactionDTO newTransactionDTO = new NewTransactionDTO(userId, operationIntentId);

        String requestBody = objectMapper.writeValueAsString(newTransactionDTO);

        mockMvc.perform(post("/transaction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value(TransactionStatus.PENDING.toString()));

        verify(transactionService).createTransaction(userId, operationIntentId);
    }


    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testCreateTransaction_userNotFound() throws Exception {
        when(transactionService.createTransaction(any(Long.class), any(Long.class)))
                .thenThrow(new UserNotFoundException());

        NewTransactionDTO request = new NewTransactionDTO(1L, 1L);

        mockMvc.perform(post("/transaction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error creating transaction: User not found"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testProcessTransaction_successful() throws Exception {
        Long transactionId = 1L;
        Long userId = 1L;
        TransactionStatus action = TransactionStatus.PENDING;

        User mockUser = new User();
        Transaction mockTransaction = Mockito.mock(Transaction.class);
        OperationIntent mockOperationIntent = new OperationIntent();
        mockOperationIntent.setSymbol(CryptoSymbol.BTCUSDT);
        when(mockTransaction.getOperationIntent()).thenReturn(mockOperationIntent);

        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(transactionService.processTransaction(transactionId, userId, action)).thenReturn(mockTransaction);

        TransactionActionDTO request = new TransactionActionDTO();
        request.setUserId(userId);
        request.setAction(action);

        mockMvc.perform(post("/transaction/process/" + transactionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
        }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testProcessTransaction_transactionNotFound() throws Exception {
        when(transactionService.processTransaction(any(Long.class), any(Long.class), any()))
                .thenThrow(new TransactionNotFoundException(1L));

        TransactionActionDTO request = new TransactionActionDTO();
        request.setUserId(1L);
        request.setAction(null);

        mockMvc.perform(post("/transaction/process/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error processing transaction: Transaction with id 1 not found"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetTransactionVolume_successful() throws Exception {
        when(transactionService.getConfirmedTransactionsByDateRange(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(10);

        mockMvc.perform(get("/transaction/getVolume")
                        .param("startDate", "2024-01-01T00:00:00")
                        .param("endDate", "2024-01-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }
}
