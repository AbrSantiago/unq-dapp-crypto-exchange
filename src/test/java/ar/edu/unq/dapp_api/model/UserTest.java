package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.builders.UserBuilder;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void userConstructorInitializesFieldsCorrectly() {
        User user = new UserBuilder().build();

        assertEquals(0, user.getPointsObtained());
        assertEquals(0, user.getOperationsPerformed());
        assertNotNull(user.getUserOperationIntents());
        assertTrue(user.getUserOperationIntents().isEmpty());
    }

    @Test
    void publishOperationIntentAddsIntentToUser() {
        User user = new UserBuilder().build();
        OperationIntent intent = user.publishOperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, IntentionType.BUY);

        assertNotNull(intent);
        assertEquals(1, user.getUserOperationIntents().size());
        assertEquals(intent, user.getUserOperationIntents().getFirst());
    }

    @Test
    void notifySentTransactionCompletesTransfer() {
        User user = new UserBuilder().build();
        Transaction transaction = mock(Transaction.class);

        user.notifySentTransaction(transaction);

        verify(transaction).completeTransfer();
    }

    @Test
    void notifyReceivedTransactionConfirmsReception() {
        User user = new UserBuilder().build();
        Transaction transaction = mock(Transaction.class);

        user.notifyReceivedTransaction(transaction);

        verify(transaction).confirmReception();
    }

    @Test
    void cancelTransactionCancelsTransactionAndDiscountsPoints() {
        User user = new UserBuilder().withPointsObtained(30).build();
        Transaction transaction = mock(Transaction.class);

        when(transaction.cancelByUserPoints()).thenReturn(20);

        user.cancelTransaction(transaction);

        verify(transaction).cancelTransaction();
        assertEquals(10, user.getPointsObtained());
    }

    @Test
    void addPointsIncreasesPointsObtained() {
        User user = new UserBuilder().withPointsObtained(10).build();

        user.addPoints(5);

        assertEquals(15, user.getPointsObtained());
    }

    @Test
    void discountPointsDecreasesPointsObtained() {
        User user = new UserBuilder().withPointsObtained(10).build();

        user.discountPoints(5);

        assertEquals(5, user.getPointsObtained());
    }
}