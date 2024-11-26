package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.exception.UserAlreadyExistsException;
import ar.edu.unq.dapp_api.exception.UserNotFoundException;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.repositories.UserRepository;
import ar.edu.unq.dapp_api.webservice.dto.user.RegisterUserDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.RequestLoginUserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    @Test
    void loginWithValidCredentialsReturnsUser() {
        RequestLoginUserDTO loginDTO = new RequestLoginUserDTO("test@example.com", "Password1!");
        User user = new User("test@example.com", "wallet123", "John", "Doe", "123 Main St", "encodedPassword", "123456789");

        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())).thenReturn(true);

        User result = userService.login(loginDTO);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).findByEmail(loginDTO.getEmail());
    }

    @Test
    void loginWithInvalidEmailThrowsUserNotFoundException() {
        RequestLoginUserDTO loginDTO = new RequestLoginUserDTO("invalid@example.com", "Password1!");

        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.login(loginDTO));
        verify(userRepository, times(1)).findByEmail(loginDTO.getEmail());
    }

    @Test
    void loginWithInvalidPasswordThrowsUserNotFoundException() {
        RequestLoginUserDTO loginDTO = new RequestLoginUserDTO("test@example.com", "WrongPassword");
        User user = new User("test@example.com", "wallet123", "John", "Doe", "123 Main St", "encodedPassword", "123456789");

        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.login(loginDTO));
        verify(userRepository, times(1)).findByEmail(loginDTO.getEmail());
    }

    @Test
    void getUserByIdWithValidIdReturnsUser() {
        User user = new User("test@example.com", "wallet123", "John", "Doe", "123 Main St", "Password1!", "123456789");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserByIdWithInvalidIdThrowsUserNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void loadUserByUsernameWithValidEmailReturnsUserDetails() {
        User user = new User("test@example.com", "wallet123", "John", "Doe", "123 Main St", "encodedPassword", "123456789");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        var userDetails = userService.loadUserByUsername("test@example.com");

        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void loadUserByUsernameWithInvalidEmailThrowsUsernameNotFoundException() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonexistent@example.com"));
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }
}
