package ar.edu.unq.dapp_api.validation;

import ar.edu.unq.dapp_api.exception.InvalidPriceException;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.service.integration.DollarService;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.NewOperationIntentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OperationIntentValidatorTest {

    @Mock
    private DollarService dollarService; // Mockear DollarService

    @InjectMocks
    private OperationIntentValidator validator; // Inyectar el mock en el validador

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializar los mocks
    }

    @Test
    void testValidOperationARSAmountWithinRange() {
        // Datos de prueba
        BigDecimal cryptoPrice = new BigDecimal("97662.79"); // Precio de BTC
        BigDecimal cryptoAmount = new BigDecimal("0.12"); // Cantidad de criptomonedas
        BigDecimal operationARSAmount = new BigDecimal("13002985"); // ARS proporcionado

        // Mockear el valor del dólar
        when(dollarService.getDollarBuyValue()).thenReturn(new BigDecimal("1123"));

        // Crear el DTO para la operación
        NewOperationIntentDTO newOperationIntentDTO = new NewOperationIntentDTO();
        newOperationIntentDTO.setType(IntentionType.BUY);
        newOperationIntentDTO.setCryptoAmount(cryptoAmount);
        newOperationIntentDTO.setOperationARSAmount(operationARSAmount);

        // Ejecutar la validación y verificar que no se lance excepción
        assertDoesNotThrow(() -> validator.validate(newOperationIntentDTO, cryptoPrice));
    }

    @Test
    void testInvalidOperationARSAmountBelowLowerLimit() {
        // Datos de prueba
        BigDecimal cryptoPrice = new BigDecimal("97662.79"); // Precio de BTC
        BigDecimal cryptoAmount = new BigDecimal("0.12"); // Cantidad de criptomonedas
        BigDecimal operationARSAmount = new BigDecimal("1300000"); // ARS fuera de rango

        // Mockear el valor del dólar
        when(dollarService.getDollarBuyValue()).thenReturn(new BigDecimal("1123"));

        // Crear el DTO para la operación
        NewOperationIntentDTO newOperationIntentDTO = new NewOperationIntentDTO();
        newOperationIntentDTO.setType(IntentionType.BUY);
        newOperationIntentDTO.setCryptoAmount(cryptoAmount);
        newOperationIntentDTO.setOperationARSAmount(operationARSAmount);

        // Ejecutar la validación y verificar que se lance una excepción
        InvalidPriceException thrown = assertThrows(InvalidPriceException.class, () -> {
            validator.validate(newOperationIntentDTO, cryptoPrice);
        });

        // Verificar el mensaje de la excepción
        assertTrue(thrown.getMessage().contains("The ARS amount is not within the expected range of 5%"));
    }

    @Test
    void testInvalidOperationARSAmountAboveUpperLimit() {
        // Datos de prueba
        BigDecimal cryptoPrice = new BigDecimal("97662.79"); // Precio de BTC
        BigDecimal cryptoAmount = new BigDecimal("0.12"); // Cantidad de criptomonedas
        BigDecimal operationARSAmount = new BigDecimal("1500000"); // ARS fuera de rango

        // Mockear el valor del dólar
        when(dollarService.getDollarBuyValue()).thenReturn(new BigDecimal("1123"));

        // Crear el DTO para la operación
        NewOperationIntentDTO newOperationIntentDTO = new NewOperationIntentDTO();
        newOperationIntentDTO.setType(IntentionType.BUY);
        newOperationIntentDTO.setCryptoAmount(cryptoAmount);
        newOperationIntentDTO.setOperationARSAmount(operationARSAmount);

        // Ejecutar la validación y verificar que se lance una excepción
        InvalidPriceException thrown = assertThrows(InvalidPriceException.class, () -> {
            validator.validate(newOperationIntentDTO, cryptoPrice);
        });

        // Verificar el mensaje de la excepción
        assertTrue(thrown.getMessage().contains("The ARS amount is not within the expected range of 5%"));
    }

    @Test
    void testNullCryptoPriceShouldThrowException() {
        // Datos de prueba con cryptoPrice nulo
        BigDecimal cryptoPrice = null;
        BigDecimal cryptoAmount = new BigDecimal("0.12");
        BigDecimal operationARSAmount = new BigDecimal("1400000");

        // Crear el DTO para la operación
        NewOperationIntentDTO newOperationIntentDTO = new NewOperationIntentDTO();
        newOperationIntentDTO.setType(IntentionType.BUY);
        newOperationIntentDTO.setCryptoAmount(cryptoAmount);
        newOperationIntentDTO.setOperationARSAmount(operationARSAmount);

        // Ejecutar la validación y verificar que se lance una excepción
        InvalidPriceException thrown = assertThrows(InvalidPriceException.class, () -> {
            validator.validate(newOperationIntentDTO, cryptoPrice);
        });

        // Verificar el mensaje de la excepción
        assertEquals("Invalid input: crypto price, amount, or ARS amount is null.", thrown.getMessage());
    }

    @Test
    void testNullDollarValueShouldThrowException() {
        // Datos de prueba con dólar nulo
        BigDecimal cryptoPrice = new BigDecimal("97662.79");
        BigDecimal cryptoAmount = new BigDecimal("0.12");
        BigDecimal operationARSAmount = new BigDecimal("1400000");

        // Mockear el valor del dólar como nulo
        when(dollarService.getDollarBuyValue()).thenReturn(null);

        // Crear el DTO para la operación
        NewOperationIntentDTO newOperationIntentDTO = new NewOperationIntentDTO();
        newOperationIntentDTO.setType(IntentionType.BUY);
        newOperationIntentDTO.setCryptoAmount(cryptoAmount);
        newOperationIntentDTO.setOperationARSAmount(operationARSAmount);

        // Ejecutar la validación y verificar que se lance una excepción
        InvalidPriceException thrown = assertThrows(InvalidPriceException.class, () -> {
            validator.validate(newOperationIntentDTO, cryptoPrice);
        });

        // Verificar el mensaje de la excepción
        assertEquals("Dollar value is null. Could not fetch current value.", thrown.getMessage());
    }
}