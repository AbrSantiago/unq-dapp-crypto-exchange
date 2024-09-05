package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OperationIntent {

    private final CryptoSymbol symbol;
    private final Long cryptoAmount;
    private final Long cryptoPrice;
    private final Long operationARSAmount;
    private final UserAccount userAccount;
    private final IntentionType type;
    private final LocalDateTime dateTime;

    public OperationIntent(CryptoSymbol symbol, Long cryptoAmount, Long cryptoPrice, Long operationARSAmount, UserAccount userAccount, IntentionType type) {
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationARSAmount = operationARSAmount;
        this.userAccount = userAccount;
        this.type = type;
        this.dateTime = LocalDateTime.now();
    }
}

