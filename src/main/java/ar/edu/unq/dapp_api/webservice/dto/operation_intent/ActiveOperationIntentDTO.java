package ar.edu.unq.dapp_api.webservice.dto.operation_intent;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.webservice.dto.user.UserAccountDTO;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ActiveOperationIntentDTO {
    private final Long id;
    private final LocalDateTime dateTime;
    private final CryptoSymbol symbol;
    private final BigDecimal cryptoAmount;
    private final BigDecimal cryptoPrice;
    private final BigDecimal operationARSAmount;
    private final UserAccountDTO user;

    public ActiveOperationIntentDTO(Long id, LocalDateTime dateTime, CryptoSymbol symbol, BigDecimal cryptoAmount, BigDecimal cryptoPrice, BigDecimal operationARSAmount, UserAccountDTO user) {
        this.id = id;
        this.dateTime = dateTime;
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationARSAmount = operationARSAmount;
        this.user = user;
    }

    public static ActiveOperationIntentDTO fromModel(OperationIntent operationIntent) {
        if (operationIntent.getUser() == null) {
            throw new NullPointerException("User cannot be null");
        }
        UserAccountDTO userDTO = UserAccountDTO.fromUser(operationIntent.getUser());
        return new ActiveOperationIntentDTO(
                operationIntent.getId(),
                operationIntent.getDateTime(),
                operationIntent.getSymbol(),
                operationIntent.getCryptoAmount(),
                operationIntent.getCryptoPrice(),
                operationIntent.getOperationARSAmount(),
                userDTO
        );
    }
}