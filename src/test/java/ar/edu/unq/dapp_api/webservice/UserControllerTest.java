package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
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
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getAllUsersReturnsListOfUsers() throws Exception {
        User user1 = new User("email1@example.com", "wallet1", "John", "Doe", "Address 1", "Password1!", "1234567890123456789012");
        User user2 = new User("email2@example.com", "wallet2", "Jane", "Doe", "Address 2", "Password2!", "1234567890123456789013");
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'email':'email1@example.com'},{'email':'email2@example.com'}]"));

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void createUserSavesAndReturnsUser() throws Exception {
        User user = new User("email@example.com", "wallet", "John", "Doe", "Address", "Password1!", "1234567890123456789012");

        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"email@example.com\",\"walletAddress\":\"wallet\",\"name\":\"John\",\"surname\":\"Doe\",\"address\":\"Address\",\"password\":\"Password1!\",\"cvu\":\"1234567890123456789012\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'email':'email@example.com'}"));

        verify(userRepository, times(1)).save(any(User.class));
    }
}