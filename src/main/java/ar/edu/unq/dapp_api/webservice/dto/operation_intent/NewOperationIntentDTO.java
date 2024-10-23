package ar.edu.unq.dapp_api.webservice.dto.operation_intent;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class NewOperationIntentDTO {

    private CryptoSymbol symbol;
    private BigDecimal cryptoAmount;
    private IntentionType type;

    public NewOperationIntentDTO(CryptoSymbol symbol, BigDecimal cryptoAmount, IntentionType type) {
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.type = type;
    }

    public NewOperationIntentDTO() {
    }

}
