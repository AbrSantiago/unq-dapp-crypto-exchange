package ar.edu.unq.dapp_api.model.builders;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OperationIntentBuilderTest {

    @Test
    void buildCreatesOperationIntentWithDefaultValues() {
        OperationIntent intent = new OperationIntentBuilder().build();

        assertNotNull(intent);
        assertEquals(CryptoSymbol.BTCUSDT, intent.getSymbol());
        assertEquals(1L, intent.getCryptoAmount().longValue()); // Assuming cryptoAmount is BigDecimal
        assertEquals(50000L, intent.getCryptoPrice().longValue()); // Assuming cryptoPrice is BigDecimal
        assertEquals(50000L, intent.getOperationARSAmount().longValue()); // Assuming operationARSAmount is BigDecimal
        assertNotNull(intent.getUser());
        assertEquals(IntentionType.BUY, intent.getType());
    }

    @Test
    void buildCreatesOperationIntentWithCustomValues() {
        User user = new UserBuilder().withId(1L).build();
        OperationIntent intent = new OperationIntentBuilder()
                .withSymbol(CryptoSymbol.ETHUSDT)
                .withCryptoAmount(new BigDecimal("2")) // Use BigDecimal
                .withCryptoPrice(new BigDecimal("50000.00")) // Use BigDecimal
                .withOperationARSAmount(new BigDecimal("6000.00")) // Use BigDecimal
                .withUser(user)
                .withType(IntentionType.SELL)
                .build();

        assertNotNull(intent);
        assertEquals(CryptoSymbol.ETHUSDT, intent.getSymbol());
        assertEquals(0, new BigDecimal("2").compareTo(intent.getCryptoAmount())); // Compare BigDecimals
        assertEquals(0, new BigDecimal("50000.00").compareTo(intent.getCryptoPrice())); // Compare BigDecimals
        assertEquals(0, new BigDecimal("6000.00").compareTo(intent.getOperationARSAmount())); // Compare BigDecimals
        assertEquals(user, intent.getUser());
        assertEquals(IntentionType.SELL, intent.getType());
    }

    @Test
    void buildThrowsExceptionWhenIntentionTypeIsNull() {
        User user = new UserBuilder().withId(1L).build();
        OperationIntentBuilder builder = new OperationIntentBuilder()
                .withSymbol(CryptoSymbol.ETHUSDT)
                .withCryptoAmount(new BigDecimal("2")) // Use BigDecimal
                .withCryptoPrice(new BigDecimal("50000.00")) // Use BigDecimal
                .withOperationARSAmount(new BigDecimal("6000.00")) // Use BigDecimal
                .withUser(user)
                .withType(null);

        assertThrows(IllegalArgumentException.class, builder::build);
    }
}
