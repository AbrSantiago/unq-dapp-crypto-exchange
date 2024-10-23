package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.exception.UserAlreadyExistsException;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.service.UserService;
import ar.edu.unq.dapp_api.webservice.dto.user.RegisterUserDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private RegisterUserDTO validUserDTO;

    @BeforeEach
    void setUp() {
        validUserDTO = new RegisterUserDTO("test@example.com", "12345678", "John", "Doe", "123 Test St", "Passw0rd!", "1234567890123456789012");
    }

    @Test
    void testGetAllUsers() throws Exception {
        UserDTO userDTO1 = new UserDTO(new User("test1@example.com", "12345678", "John", "Doe", "123 Test St", "Passw0rd!", "1234567890123456789012"));
        UserDTO userDTO2 = new UserDTO(new User("test2@example.com", "87654321", "Jane", "Doe", "456 Another St", "Password123!", "8765432198765432109876"));

        Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(userDTO1.toModel(), userDTO2.toModel()));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test1@example.com"))
                .andExpect(jsonPath("$[1].email").value("test2@example.com"));
    }

    @Test
    void testCreateUser_Success() throws Exception {
        User newUser = validUserDTO.toModel();
        Mockito.when(userService.registerUser(any(RegisterUserDTO.class))).thenReturn(newUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.walletAddress").value("12345678"));
    }

    @Test
    void testCreateUser_UserAlreadyExists() throws Exception {
        Mockito.when(userService.registerUser(any(RegisterUserDTO.class))).thenThrow(new UserAlreadyExistsException());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Error creating user: User already exists"));
    }

    @Test
    void testCreateUser_InvalidUserData() throws Exception {
        RegisterUserDTO invalidUserDTO = new RegisterUserDTO("", "", "", "", "", "weakpassword", "");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserDTO)))
                .andExpect(status().isBadRequest());
    }

}
