package ar.edu.unq.dapp_api.webservice.dto.operation_intent;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.webservice.dto.user.UserAccountDTO;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ActiveOperationIntentDTO {

    private Long id;
    private LocalDateTime dateTime;
    private CryptoSymbol symbol;
    private BigDecimal cryptoAmount;
    private BigDecimal cryptoPrice;
    private BigDecimal operationARSAmount;
    private UserAccountDTO user;

    public ActiveOperationIntentDTO(Long id, LocalDateTime startTime, CryptoSymbol symbol, BigDecimal cryptoAmount, BigDecimal cryptoPrice, BigDecimal operationARSAmount, UserAccountDTO user) {
        this.id = id;
        this.dateTime = startTime;
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationARSAmount = operationARSAmount;
        this.user = user;
    }

    public ActiveOperationIntentDTO() {
    }

    public static ActiveOperationIntentDTO fromModel(OperationIntent operationIntent) {
        return new ActiveOperationIntentDTO(
                operationIntent.getId(),
                operationIntent.getDateTime(),
                operationIntent.getSymbol(),
                operationIntent.getCryptoAmount(),
                operationIntent.getCryptoPrice(),
                operationIntent.getOperationARSAmount(),
                UserAccountDTO.fromUser(operationIntent.getUser())
        );
    }
}