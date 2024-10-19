package ar.edu.unq.dapp_api.service;

import ar.edu.unq.dapp_api.exception.UserAlreadyExistsException;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.repositories.UserRepository;
import ar.edu.unq.dapp_api.service.impl.UserServiceImpl;
import ar.edu.unq.dapp_api.webservice.dto.user.RegisterUserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Validator validator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUserWithValidData() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("test@example.com", "wallet123", "John", "Doe", "123 Main St", "Password1!", "123456789");
        User user = new User("test@example.com", "wallet123", "John", "Doe", "123 Main St", "Password1!", "123456789");

        when(validator.validate(registerUserDTO)).thenReturn(Collections.emptySet());
        when(userRepository.existsByEmail(registerUserDTO.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(registerUserDTO.getPassword())).thenReturn("encodedPassword");

        User result = userService.registerUser(registerUserDTO);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUserWithInvalidEmailThrowsConstraintViolationException() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("invalid-email", "wallet123", "John", "Doe", "123 Main St", "Password1!", "123456789");
        Set<ConstraintViolation<RegisterUserDTO>> violations = Set.of(mock(ConstraintViolation.class));

        when(validator.validate(registerUserDTO)).thenReturn(violations);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(registerUserDTO));
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUserWithExistingEmailThrowsUserAlreadyExistsException() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("test@example.com", "wallet123", "John", "Doe", "123 Main St", "Password1!", "123456789");

        when(validator.validate(registerUserDTO)).thenReturn(Collections.emptySet());
        when(userRepository.existsByEmail(registerUserDTO.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(registerUserDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getAllUsersReturnsNonEmptyList() {
        List<User> users = List.of(
                new User("test1@example.com", "wallet123", "John", "Doe", "123 Main St", "Password1!", "123456789"),
                new User("test2@example.com", "wallet456", "Jane", "Doe", "456 Main St", "Password2!", "987654321")
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllUsersReturnsEmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }
}
