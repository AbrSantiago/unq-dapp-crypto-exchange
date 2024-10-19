package ar.edu.unq.dapp_api.webservice.dto.operationIntent;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ActiveOperationIntentDTO {

    private Long id;
    private LocalDateTime dateTime;
    private CryptoSymbol symbol;
    private Long cryptoAmount;
    private Float cryptoPrice;
    private Long operationARSAmount;
    private String userName;
    private String userSurname;
    private int userAmountOfOperations;
    private int userReputation;

    public ActiveOperationIntentDTO(Long id, LocalDateTime startTime, CryptoSymbol symbol, Long cryptoAmount, Float cryptoPrice, Long operationARSAmount, String userName, String userSurname, int userAmountOfOperations, int userReputation) {
        this.id = id;
        this.dateTime = startTime;
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationARSAmount = operationARSAmount;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userAmountOfOperations = userAmountOfOperations;
        this.userReputation = userReputation;
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
                operationIntent.getUser().getName(),
                operationIntent.getUser().getSurname(),
                operationIntent.getUser().getOperationsPerformed(),
                operationIntent.getUser().getReputation()
        );
    }

}
