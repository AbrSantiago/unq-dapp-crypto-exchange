package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.exception.DuplicateResourceException;
import ar.edu.unq.dapp_api.exception.GlobalExceptionHandler;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("createUser should return 201 Created when user is successfully created")
    void createUserShouldReturnCreatedWhenUserIsSuccessfullyCreated() throws Exception {
        User user = new User("john.doe@example.com", "12345678", "John", "Doe", "123 Main St", "SecurePass1!", "1234567890123456789012");
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"surname\":\"Doe\",\"email\":\"john.doe@example.com\",\"address\":\"123 Main St\",\"password\":\"SecurePass1!\",\"cvu\":\"1234567890123456789012\",\"walletAddress\":\"12345678\",\"pointsObtained\":0,\"operationsPerformed\":0}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"name\":\"John\",\"surname\":\"Doe\",\"email\":\"john.doe@example.com\",\"address\":\"123 Main St\",\"password\":\"SecurePass1!\",\"cvu\":\"1234567890123456789012\",\"walletAddress\":\"12345678\",\"pointsObtained\":0,\"operationsPerformed\":0}"));
    }

    @Test
    @DisplayName("createUser should return 409 Conflict when email, CVU, or wallet address already exists")
    void createUserShouldReturnConflictWhenEmailCVUOrWalletAddressAlreadyExists() throws Exception {
        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException("Duplicate entry"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"surname\":\"Doe\",\"email\":\"john.doe@example.com\",\"address\":\"123 Main St\",\"password\":\"SecurePass1!\",\"cvu\":\"1234567890123456789012\",\"walletAddress\":\"12345678\",\"pointsObtained\":0,\"operationsPerformed\":0}"))
                .andExpect(status().isConflict())
                .andExpect(content().string("A user with the same email, CVU, or wallet address already exists."));
    }

    @Test
    @DisplayName("getAllUsers should return 200 OK with empty list when no users exist")
    void getAllUsersShouldReturnOkWithEmptyListWhenNoUsersExist() throws Exception {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("getAllUsers should return 200 OK with list of users when users exist")
    void getAllUsersShouldReturnOkWithListOfUsersWhenUsersExist() throws Exception {
        User user = new User("john.doe@example.com", "12345678", "John", "Doe", "123 Main St", "SecurePass1!", "1234567890123456789012");
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"John\",\"surname\":\"Doe\",\"email\":\"john.doe@example.com\",\"address\":\"123 Main St\",\"password\":\"SecurePass1!\",\"cvu\":\"1234567890123456789012\",\"walletAddress\":\"12345678\",\"pointsObtained\":0,\"operationsPerformed\":0}]"));
    }
}