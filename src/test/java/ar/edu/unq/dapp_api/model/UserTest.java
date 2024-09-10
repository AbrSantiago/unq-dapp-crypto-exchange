package ar.edu.unq.dapp_api.model;

import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private final Validator validator;

    UserTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testUserConstructor() {
        User user = new User("test@example.com", "12345678", "John", "Doe", "123 Main St", "Password1!", "1234567890123456789012");

        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("Password1!", user.getPassword());
        assertEquals("1234567890123456789012", user.getCvu());
        assertEquals("12345678", user.getWalletAddress());
    }

    @Test
    void testUserSettersAndGetters() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("John");
        user.setSurname("Doe");
        user.setAddress("123 Main St");
        user.setPassword("Password1!");
        user.setCvu("1234567890123456789012");
        user.setWalletAddress("12345678");

        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("Password1!", user.getPassword());
        assertEquals("1234567890123456789012", user.getCvu());
        assertEquals("12345678", user.getWalletAddress());
    }

    @Test
    void testInvalidUserEmail() {
        User user = new User("invalid-email", "12345678", "John", "Doe", "123 Main St", "Password1!", "1234567890123456789012");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testInvalidUserPassword() {
        User user = new User("test@example.com", "12345678", "John", "Doe", "123 Main St", "password", "1234567890123456789012");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testInvalidUserCvu() {
        User user = new User("test@example.com", "12345678", "John", "Doe", "123 Main St", "Password1!", "123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testInvalidUserWalletAddress() {
        User user = new User("test@example.com", "123", "John", "Doe", "123 Main St", "Password1!", "1234567890123456789012");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }
}