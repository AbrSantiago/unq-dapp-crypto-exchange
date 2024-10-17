package ar.edu.unq.dapp_api.webservice.dto;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import lombok.Getter;

@Getter
public class NewOperationIntentDTO {

    private CryptoSymbol symbol;
    private Long cryptoAmount;
    private IntentionType type;

    public NewOperationIntentDTO(CryptoSymbol symbol, Long cryptoAmount, IntentionType type) {
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.type = type;
    }

    public NewOperationIntentDTO() {
    }

}
