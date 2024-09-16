package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.builders.UserBuilder;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void constructorInitializesFieldsCorrectly() {
        User seller = new UserBuilder().withId(1L).build();
        User buyer = new UserBuilder().withId(2L).build();
        OperationIntent intent = new OperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, seller, IntentionType.SELL);
        Transaction transaction = new Transaction(intent, seller, buyer);

        assertEquals(intent, transaction.getOperationIntent());
        assertEquals(seller, transaction.getSeller());
        assertEquals(buyer, transaction.getBuyer());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
        assertNotNull(transaction.getStartTime());
    }

    @Test
    void completeTransferSetsStatusToTransferred() {
        User seller = new UserBuilder().withId(1L).build();
        User buyer = new UserBuilder().withId(2L).build();
        OperationIntent intent = new OperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, seller, IntentionType.SELL);
        Transaction transaction = new Transaction(intent, seller, buyer);

        transaction.completeTransfer();

        assertEquals(TransactionStatus.TRANSFERRED, transaction.getStatus());
    }

    @Test
    void completeTransferThrowsExceptionWhenAlreadyTransferred() {
        User seller = new UserBuilder().withId(1L).build();
        User buyer = new UserBuilder().withId(2L).build();
        OperationIntent intent = new OperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, seller, IntentionType.SELL);
        Transaction transaction = new Transaction(intent, seller, buyer);
        transaction.completeTransfer();

        assertThrows(IllegalArgumentException.class, transaction::completeTransfer);
    }

    @Test
    void confirmReceptionSetsStatusToConfirmed() {
        User seller = new UserBuilder().withId(1L).build();
        User buyer = new UserBuilder().withId(2L).build();
        OperationIntent intent = new OperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, seller, IntentionType.SELL);
        Transaction transaction = new Transaction(intent, seller, buyer);
        transaction.completeTransfer();

        transaction.confirmReception();

        assertEquals(TransactionStatus.CONFIRMED, transaction.getStatus());
        assertEquals(OperationStatus.CLOSED, intent.getStatus());
    }

    @Test
    void confirmReceptionThrowsExceptionWhenNotTransferred() {
        User seller = new UserBuilder().withId(1L).build();
        User buyer = new UserBuilder().withId(2L).build();
        OperationIntent intent = new OperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, seller, IntentionType.SELL);
        Transaction transaction = new Transaction(intent, seller, buyer);

        assertThrows(IllegalArgumentException.class, transaction::confirmReception);
    }

    @Test
    void cancelTransactionSetsStatusToCancelled() {
        User seller = new UserBuilder().withId(1L).build();
        User buyer = new UserBuilder().withId(2L).build();
        OperationIntent intent = new OperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, seller, IntentionType.SELL);
        Transaction transaction = new Transaction(intent, seller, buyer);

        transaction.cancelTransaction();

        assertEquals(TransactionStatus.CANCELLED, transaction.getStatus());
        assertEquals(OperationStatus.CLOSED, intent.getStatus());
    }

    @Test
    void getDirectionReturnsSellerCvuForSellIntention() {
        User seller = new UserBuilder().withId(1L).withCvu("1234512345123451234512").build();
        User buyer = new UserBuilder().withId(2L).build();
        OperationIntent intent = new OperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, seller, IntentionType.SELL);
        Transaction transaction = new Transaction(intent, seller, buyer);

        assertEquals("1234512345123451234512", transaction.getDirection());
    }

    @Test
    void getDirectionReturnsBuyerWalletAddressForBuyIntention() {
        User seller = new UserBuilder().withId(1L).build();
        User buyer = new UserBuilder().withId(2L).withWalletAddress("12341234").build();
        OperationIntent intent = new OperationIntent(CryptoSymbol.BTCUSDT, 1L, 50000L, 50000L, seller, IntentionType.BUY);
        Transaction transaction = new Transaction(intent, seller, buyer);

        assertEquals("12341234", transaction.getDirection());
    }
}
