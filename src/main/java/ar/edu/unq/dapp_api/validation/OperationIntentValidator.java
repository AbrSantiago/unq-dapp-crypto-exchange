package ar.edu.unq.dapp_api.validation;

import ar.edu.unq.dapp_api.exception.InvalidPriceException;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.service.integration.DollarService;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.NewOperationIntentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OperationIntentValidator {

    private final DollarService dollarService;

    @Autowired
    public OperationIntentValidator(DollarService dollarService) {
        this.dollarService = dollarService;
    }

    public void validate(NewOperationIntentDTO newOperationIntentDTO, BigDecimal cryptoPrice) {
        IntentionType type = newOperationIntentDTO.getType();
        BigDecimal cryptoAmount = newOperationIntentDTO.getCryptoAmount();
        BigDecimal operationARSAmount = newOperationIntentDTO.getOperationARSAmount();

        if (cryptoPrice == null || cryptoAmount == null || operationARSAmount == null) {
            throw new InvalidPriceException("Invalid input: crypto price, amount, or ARS amount is null.");
        }

        BigDecimal dollarValue = type == IntentionType.BUY
                ? dollarService.getDollarBuyValue()
                : dollarService.getDollarSellValue();

        if (dollarValue == null) {
            throw new InvalidPriceException("Dollar value is null. Could not fetch current value.");
        }

        BigDecimal localARSAmount = cryptoAmount
                .multiply(cryptoPrice)       // Precio de la criptomoneda
                .multiply(dollarValue);      // Valor del dólar

        BigDecimal lowerLimit = localARSAmount.multiply(BigDecimal.valueOf(0.95));  // 5% menos
        BigDecimal upperLimit = localARSAmount.multiply(BigDecimal.valueOf(1.05));  // 5% más

        if (operationARSAmount.compareTo(lowerLimit) < 0 || operationARSAmount.compareTo(upperLimit) > 0) {
            throw new InvalidPriceException(
                    String.format("The ARS amount is not within the expected range of 5%%. The valid range is between %.2f ARS and %.2f ARS.",
                            lowerLimit, upperLimit)
            );
        }
    }
}
