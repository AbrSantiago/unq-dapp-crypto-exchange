package ar.edu.unq.dapp_api.webservice.dto.operation_intent;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class NewOperationIntentDTO {

    private CryptoSymbol symbol;
    private BigDecimal cryptoAmount;
    private BigDecimal operationARSAmount;
    private IntentionType type;

    public NewOperationIntentDTO(CryptoSymbol symbol, BigDecimal cryptoAmount, BigDecimal operationARSAmount,IntentionType type) {
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.operationARSAmount = operationARSAmount;
        this.type = type;
    }

    public NewOperationIntentDTO() {
    }

}
