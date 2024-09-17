package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.builders.OperationIntentBuilder;
import ar.edu.unq.dapp_api.model.builders.UserBuilder;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationIntentTest {

    @Test
    void constructorInitializesFieldsCorrectly() {
        User user = new UserBuilder().withId(1L).build();
        OperationIntent intent = new OperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, user, IntentionType.BUY);

        assertEquals(CryptoSymbol.BTCUSDT, intent.getSymbol());
        assertEquals(1L, intent.getCryptoAmount());
        assertEquals(50000L, intent.getCryptoPrice());
        assertEquals(50000L, intent.getOperationARSAmount());
        assertEquals(user, intent.getUser());
        assertEquals(IntentionType.BUY, intent.getType());
        assertNotNull(intent.getDateTime());
        assertEquals(OperationStatus.OPEN, intent.getStatus());
    }

    @Test
    void generateTransactionCreatesTransactionForBuyIntention() {
        User user = new UserBuilder().withId(1L).build();
        User interestedUser = new UserBuilder().withId(2L).build();
        OperationIntent intent = new OperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, user, IntentionType.BUY);

        Transaction transaction = intent.generateTransaction(interestedUser, 50000.0);

        assertNotNull(transaction);
        assertEquals(user, transaction.getBuyer());
        assertEquals(interestedUser, transaction.getSeller());
    }

    @Test
    void generateTransactionCreatesTransactionForSellIntention() {
        User user = new UserBuilder().withId(1L).build();
        User interestedUser = new UserBuilder().withId(2L).build();
        OperationIntent intent = new OperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, user, IntentionType.SELL);

        Transaction transaction = intent.generateTransaction(interestedUser, 50000.0);

        assertNotNull(transaction);
        assertEquals(user, transaction.getSeller());
        assertEquals(interestedUser, transaction.getBuyer());
    }

    @Test
    void generateTransactionThrowsExceptionWhenUserIsSame() {
        User user = new UserBuilder().withId(1L).build();
        OperationIntent intent = new OperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, user, IntentionType.BUY);

        assertThrows(IllegalArgumentException.class, () -> intent.generateTransaction(user, 50000.0));
    }

    @Test
    void generateTransactionCancelsTransactionWhenPriceIsTooLow() {
        User interestedUser = new UserBuilder().withId(2L).build();
        OperationIntent intent = new OperationIntentBuilder().withCryptoPrice(50000L).build();

        Transaction transaction = intent.generateTransaction(interestedUser, 47000.0);

        assertEquals(TransactionStatus.CANCELLED, transaction.getStatus());
    }

    @Test
    void generateTransactionCancelsTransactionWhenPriceIsTooHigh() {
        User interestedUser = new UserBuilder().withId(2L).build();
        OperationIntent intent = new OperationIntentBuilder().withCryptoPrice(50000L).build();

        Transaction transaction = intent.generateTransaction(interestedUser, 53000.0);

        assertEquals(TransactionStatus.CANCELLED, transaction.getStatus());
    }

    @Test
    void closeSetsStatusToClosed() {
        OperationIntent intent = new OperationIntentBuilder().build();

        intent.close();

        assertEquals(OperationStatus.CLOSED, intent.getStatus());
    }
}
