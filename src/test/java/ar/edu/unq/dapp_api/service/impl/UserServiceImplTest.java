package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.exception.UserAlreadyExistsException;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.webservice.dto.RegisterUserDTO;
import ar.edu.unq.dapp_api.service.UserService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private final UserService userService;

    UserServiceImplTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    void registerUserShouldCreateNewUser() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("john.doe@example.com", "wallet123", "John", "Doe", "123 Main St", "password123", "123456789");
        User result = userService.registerUser(registerUserDTO);

        assertNotNull(result);
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("wallet123", result.getWalletAddress());
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
        assertEquals("123 Main St", result.getAddress());
        assertEquals("password123", result.getPassword());
        assertEquals("123456789", result.getCvu());
    }

    @Test
    void registerUserShouldThrowExceptionIfUserAlreadyExists() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("john.doe@example.com", "wallet123", "John", "Doe", "123 Main St", "password123", "123456789");

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(registerUserDTO));
    }

    @Test
    void registerUserShouldThrowExceptionIfEmailAlreadyExists() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("john.doe@example.com", "wallet123", "John", "Doe", "123 Main St", "password123", "123456789");

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(registerUserDTO));
    }

    @Test
    void registerUserShouldThrowExceptionIfCvuAlreadyExists() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("john.doe@example.com", "wallet123", "John", "Doe", "123 Main St", "password123", "123456789");

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(registerUserDTO));
    }
}