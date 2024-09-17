package ar.edu.unq.dapp_api.model.builders;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationIntentBuilderTest {

    @Test
    void buildCreatesOperationIntentWithDefaultValues() {
        OperationIntent intent = new OperationIntentBuilder().build();

        assertNotNull(intent);
        assertEquals(CryptoSymbol.BTCUSDT, intent.getSymbol());
        assertEquals(1L, intent.getCryptoAmount());
        assertEquals(50000L, intent.getCryptoPrice());
        assertEquals(50000L, intent.getOperationARSAmount());
        assertNotNull(intent.getUser());
        assertEquals(IntentionType.BUY, intent.getType());
    }

    @Test
    void buildCreatesOperationIntentWithCustomValues() {
        User user = new UserBuilder().withId(1L).build();
        OperationIntent intent = new OperationIntentBuilder()
                .withSymbol(CryptoSymbol.ETHUSDT)
                .withCryptoAmount(2L)
                .withCryptoPrice(3000L)
                .withOperationARSAmount(6000L)
                .withUser(user)
                .withType(IntentionType.SELL)
                .build();

        assertNotNull(intent);
        assertEquals(CryptoSymbol.ETHUSDT, intent.getSymbol());
        assertEquals(2L, intent.getCryptoAmount());
        assertEquals(3000L, intent.getCryptoPrice());
        assertEquals(6000L, intent.getOperationARSAmount());
        assertEquals(user, intent.getUser());
        assertEquals(IntentionType.SELL, intent.getType());
    }

    @Test
    void buildThrowsExceptionWhenIntentionTypeIsNull() {
        User user = new UserBuilder().withId(1L).build();
        OperationIntentBuilder builder = new OperationIntentBuilder()
                .withSymbol(CryptoSymbol.ETHUSDT)
                .withCryptoAmount(2L)
                .withCryptoPrice(3000L)
                .withOperationARSAmount(6000L)
                .withUser(user)
                .withType(null);

        assertThrows(IllegalArgumentException.class, builder::build);
    }
}