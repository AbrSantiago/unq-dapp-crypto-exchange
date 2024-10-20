package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.exception.UserAlreadyExistsException;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.builders.UserBuilder;
import ar.edu.unq.dapp_api.service.UserService;
import ar.edu.unq.dapp_api.webservice.dto.user.RegisterUserDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsersReturnsEmptyListWhenNoUsersExist() {
        when(userService.getAllUsers()).thenReturn(List.of());

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).isEmpty());
    }

    @Test
    void getAllUsersReturnsListOfUsers() {
        User mockUser = new UserBuilder().build();
        when(userService.getAllUsers()).thenReturn(List.of(mockUser));

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void createUserReturnsBadRequestWhenUserAlreadyExists() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        when(userService.registerUser(registerUserDTO)).thenThrow(new UserAlreadyExistsException("User already exists"));

        assertThrows(UserAlreadyExistsException.class, () -> userController.createUser(registerUserDTO));
    }

    @Test
    void createUserReturnsOkWhenUserIsCreated() {
        RegisterUserDTO dto = new RegisterUserDTO();
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        when(userService.registerUser(dto)).thenReturn(user);

        ResponseEntity<Object> response = userController.createUser(dto);

        UserDTO responseBody = (UserDTO) response.getBody();
        assert responseBody != null;
        assertEquals("Test User", responseBody.getName());
        assertEquals("test@example.com", responseBody.getEmail());

        verify(userService, times(1)).registerUser(dto);
    }

}
