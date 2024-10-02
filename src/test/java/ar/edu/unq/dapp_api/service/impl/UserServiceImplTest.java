package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.exception.UserAlreadyExistsException;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUserSuccessfully() {
        User user = new User("email@example.com", "12345678", "John", "Doe", "123 Main St", "SecurePass1!", "1234567890123456789012");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByCvu(user.getCvu())).thenReturn(false);
        when(userRepository.existsByWalletAddress(user.getWalletAddress())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.registerUser(user);

        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @Test
    void registerUserThrowsExceptionWhenEmailExists() {
        User user = new User("email@example.com", "12345678", "John", "Doe", "123 Main St", "SecurePass1!", "1234567890123456789012");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(user));
        verify(userRepository, never()).save(user);
    }

    @Test
    void registerUserThrowsExceptionWhenCvuExists() {
        User user = new User("email@example.com", "12345678", "John", "Doe", "123 Main St", "SecurePass1!", "1234567890123456789012");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByCvu(user.getCvu())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(user));
        verify(userRepository, never()).save(user);
    }

    @Test
    void registerUserThrowsExceptionWhenWalletAddressExists() {
        User user = new User("email@example.com", "12345678", "John", "Doe", "123 Main St", "SecurePass1!", "1234567890123456789012");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByCvu(user.getCvu())).thenReturn(false);
        when(userRepository.existsByWalletAddress(user.getWalletAddress())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(user));
        verify(userRepository, never()).save(user);
    }

    @Test
    void getAllUsersReturnsListOfUsers() {
        List<User> users = List.of(
                new User("email1@example.com", "12345678", "John", "Doe", "123 Main St", "SecurePass1!", "1234567890123456789012"),
                new User("email2@example.com", "87654321", "Jane", "Doe", "456 Main St", "SecurePass2!", "2109876543210987654321")
        );
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(users, result);
        verify(userRepository).findAll();
    }
}