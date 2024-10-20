package ar.edu.unq.dapp_api.webservice.dto.operation_intent;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ExpressedOperationIntentDTO {

    private final Long id;
    private final CryptoSymbol symbol;
    private final BigDecimal cryptoAmount;
    private final BigDecimal cryptoPrice;
    private final BigDecimal operationARSAmount;
    private final String userName;
    private final String userSurname;
    private final IntentionType type;

    ExpressedOperationIntentDTO(Long id, CryptoSymbol symbol, BigDecimal cryptoAmount, BigDecimal cryptoPrice, BigDecimal operationARSAmount, String userName, String userSurname, IntentionType type) {
        this.id = id;
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationARSAmount = operationARSAmount;
        this.userName = userName;
        this.userSurname = userSurname;
        this.type = type;
    }

    public static ExpressedOperationIntentDTO fromModel(OperationIntent operationIntent) {
        return new ExpressedOperationIntentDTO(
                operationIntent.getId(),
                operationIntent.getSymbol(),
                operationIntent.getCryptoAmount(),
                operationIntent.getCryptoPrice(),
                operationIntent.getOperationARSAmount(),
                operationIntent.getUser().getName(),
                operationIntent.getUser().getSurname(),
                operationIntent.getType()
        );
    }

}
