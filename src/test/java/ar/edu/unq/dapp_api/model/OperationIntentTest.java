package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.builders.OperationIntentBuilder;
import ar.edu.unq.dapp_api.model.builders.UserBuilder;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OperationIntentTest {

    private OperationIntent operationIntent;
    private User user;

    @BeforeEach
    void setUp() {
        user = new UserBuilder().withId(1L).build();
        operationIntent = new OperationIntentBuilder()
                .withUser(user)
                .build();
    }


    @Test
    void generateTransactionCreatesTransactionWithCorrectValues() {
        User interestedUser = new UserBuilder().withId(2L).build();
        Transaction transaction = operationIntent.generateTransaction(interestedUser, BigDecimal.valueOf(50000));

        assertNotNull(transaction);
        assertEquals(operationIntent, transaction.getOperationIntent());
        assertEquals(interestedUser, transaction.getSeller());
        assertEquals(user, transaction.getBuyer());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
    }

    @Test
    void validateTransactionCancelsTransactionWhenPriceOutOfBounds() {
        User interestedUser = new UserBuilder().withId(2L).build();
        operationIntent.generateTransaction(interestedUser, BigDecimal.valueOf(50000));
        Transaction transaction = operationIntent.getTransaction();

        operationIntent.validateTransaction(BigDecimal.valueOf(60000));

        assertTrue(transaction.isCanceled());
    }

    @Test
    void validateTransactionDoesNotCancelTransactionWhenPriceWithinBounds() {
        User interestedUser = new UserBuilder().withId(2L).build();
        operationIntent.generateTransaction(interestedUser, BigDecimal.valueOf(50000));
        Transaction transaction = operationIntent.getTransaction();

        operationIntent.validateTransaction(BigDecimal.valueOf(52000));

        assertFalse(transaction.isCanceled());
    }

    @Test
    void closeChangesStatusToClosed() {
        operationIntent.close();
        assertEquals(OperationStatus.CLOSED, operationIntent.getStatus());
    }

    @Test
    void checkCancelTransactionReturnsTrueWhenPriceBelowLowerBound() {
        BigDecimal currentPrice = BigDecimal.valueOf(50000);
        operationIntent = new OperationIntentBuilder()
                .withCryptoPrice(BigDecimal.valueOf(47000))
                .withUser(user)
                .build();

        assertTrue(operationIntent.checkCancelTransaction(currentPrice));
    }

    @Test
    void checkCancelTransactionReturnsTrueWhenPriceAboveUpperBound() {
        BigDecimal currentPrice = BigDecimal.valueOf(50000);
        operationIntent = new OperationIntentBuilder()
                .withCryptoPrice(BigDecimal.valueOf(53000))
                .withUser(user)
                .build();

        assertTrue(operationIntent.checkCancelTransaction(currentPrice));
    }

    @Test
    void checkCancelTransactionReturnsFalseWhenPriceWithinBounds() {
        BigDecimal currentPrice = BigDecimal.valueOf(50000);
        operationIntent = new OperationIntentBuilder()
                .withCryptoPrice(BigDecimal.valueOf(51000))
                .withUser(user)
                .build();

        assertFalse(operationIntent.checkCancelTransaction(currentPrice));
    }
}
