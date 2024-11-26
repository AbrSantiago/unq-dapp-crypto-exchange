package ar.edu.unq.dapp_api.webservice;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.NewOperationIntentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.service.OperationIntentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
class OperationIntentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperationIntentService operationIntentService;

    @Autowired
    private OperationIntentController operationIntentController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(operationIntentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testCreateOperationIntent() throws Exception {
        NewOperationIntentDTO newOperationIntentDTO = new NewOperationIntentDTO(
                CryptoSymbol.BTCUSDT,
                BigDecimal.valueOf(0.5),
                BigDecimal.valueOf(0.5),
                IntentionType.BUY
        );

        OperationIntent operationIntent = new OperationIntent();
        operationIntent.setId(1L);
        operationIntent.setSymbol(CryptoSymbol.BTCUSDT);
        operationIntent.setCryptoAmount(BigDecimal.valueOf(0.5));
        operationIntent.setType(IntentionType.BUY);
        operationIntent.setUser(new User());

        when(operationIntentService.createOperationIntent(any(Long.class), any(NewOperationIntentDTO.class)))
                .thenReturn(operationIntent);

        mockMvc.perform(post("/operation-intent/create/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOperationIntentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.symbol").value("BTCUSDT"))
                .andExpect(jsonPath("$.cryptoAmount").value(0.5))
                .andExpect(jsonPath("$.type").value("BUY"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetActiveOperationIntentsFromUser() throws Exception {
        OperationIntent operationIntent1 = new OperationIntent();
        operationIntent1.setId(1L);
        operationIntent1.setSymbol(CryptoSymbol.BTCUSDT);
        operationIntent1.setCryptoAmount(BigDecimal.valueOf(0.5));
        operationIntent1.setType(IntentionType.BUY);
        operationIntent1.setUser(new User());

        OperationIntent operationIntent2 = new OperationIntent();
        operationIntent2.setId(2L);
        operationIntent2.setSymbol(CryptoSymbol.ETHUSDT);
        operationIntent2.setCryptoAmount(BigDecimal.valueOf(1.0));
        operationIntent2.setType(IntentionType.SELL);
        operationIntent2.setUser(new User());

        List<OperationIntent> activeOperationIntents = List.of(operationIntent1, operationIntent2);

        when(operationIntentService.getActivesOperationIntentsFromUser(1L)).thenReturn(activeOperationIntents);

        mockMvc.perform(get("/operation-intent/actives/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].symbol").value("BTCUSDT"))
                .andExpect(jsonPath("$[0].cryptoAmount").value(0.5))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].symbol").value("ETHUSDT"))
                .andExpect(jsonPath("$[1].cryptoAmount").value(1.0));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void createOperationIntent_InvalidUserId() throws Exception {
        NewOperationIntentDTO newOperationIntentDTO = new NewOperationIntentDTO(
                CryptoSymbol.BTCUSDT,
                BigDecimal.valueOf(0.5),
                BigDecimal.valueOf(0.5),
                IntentionType.BUY
        );

        mockMvc.perform(post("/operation-intent/create/invalidUserId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOperationIntentDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getActiveOperationIntentsFromUser_NoActiveIntents() throws Exception {
        when(operationIntentService.getActivesOperationIntentsFromUser(1L)).thenReturn(List.of());

        mockMvc.perform(get("/operation-intent/actives/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getActiveOperationIntentsFromUser_InternalServerError() throws Exception {
        when(operationIntentService.getActivesOperationIntentsFromUser(1L)).thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(get("/operation-intent/actives/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error getting active operation intents for a user: Service error"));
    }
}
