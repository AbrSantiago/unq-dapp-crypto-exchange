package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.exception.UserAlreadyExistsException;
import ar.edu.unq.dapp_api.exception.UserNotFoundException;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.service.UserService;
import ar.edu.unq.dapp_api.config.util.JwtUtil;
import ar.edu.unq.dapp_api.webservice.dto.user.RegisterUserDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.RequestLoginUserDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private RegisterUserDTO validUserDTO;

    @BeforeEach
    void setUp() {
        validUserDTO = new RegisterUserDTO("test@example.com", "12345678", "John", "Doe", "123 Test St", "Passw0rd!", "1234567890123456789012");
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
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
    @WithMockUser(username = "user", roles = {"USER"})
    void testRegister_Success() throws Exception {
        User newUser = validUserDTO.toModel();
        Mockito.when(userService.registerUser(any(RegisterUserDTO.class))).thenReturn(newUser);

        mockMvc.perform(post("/users/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.walletAddress").value("12345678"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testRegisterAlreadyExists() throws Exception {
        Mockito.when(userService.registerUser(any(RegisterUserDTO.class))).thenThrow(new UserAlreadyExistsException());

        mockMvc.perform(post("/users/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Error creating user: User already exists"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testRegisterData() throws Exception {
        RegisterUserDTO invalidUserDTO = new RegisterUserDTO("", "", "", "", "", "weakpassword", "");

        mockMvc.perform(post("/users/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testLogin_Success() throws Exception {
        RequestLoginUserDTO loginUserDTO = new RequestLoginUserDTO("test@example.com", "Passw0rd!");
        User user = validUserDTO.toModel();
        String mockToken = "mock-jwt-token";

        Mockito.when(userService.login(any(RequestLoginUserDTO.class))).thenReturn(user);
        Mockito.when(jwtUtil.generateToken(user.getEmail())).thenReturn(mockToken);

        mockMvc.perform(post("/users/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDTO)))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer mock-jwt-token"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testLogin_UserNotFound() throws Exception {
        RequestLoginUserDTO loginUserDTO = new RequestLoginUserDTO("nonexistent@example.com", "InvalidPassword");

        Mockito.when(userService.login(any(RequestLoginUserDTO.class)))
                .thenThrow(new UserNotFoundException());

        mockMvc.perform(post("/users/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testRegister_UnexpectedError() throws Exception {
        Mockito.when(userService.registerUser(any(RegisterUserDTO.class)))
                .thenThrow(new RuntimeException("Unexpected server error"));

        mockMvc.perform(post("/users/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserDTO)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An unexpected error occurred: Unexpected server error"));
    }

}
