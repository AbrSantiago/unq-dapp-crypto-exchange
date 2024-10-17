package ar.edu.unq.dapp_api.webservice.dto.operationIntent;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.webservice.dto.user.SimpleUserDTO;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OperationIntentDTO {

    private Long id;
    private CryptoSymbol symbol;
    private Long cryptoAmount;
    private Float cryptoPrice;
    private Long operationARSAmount;
    private IntentionType type;
    private LocalDateTime dateTime;
    private OperationStatus status;
    private Transaction transaction;
    private SimpleUserDTO user;

    public OperationIntentDTO(Long id, CryptoSymbol symbol, Long cryptoAmount, Float cryptoPrice, Long operationARSAmount, IntentionType type, LocalDateTime dateTime, OperationStatus status, Transaction transaction, SimpleUserDTO user) {
        this.id = id;
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationARSAmount = operationARSAmount;
        this.type = type;
        this.dateTime = dateTime;
        this.status = status;
        this.transaction = transaction;
        this.user = user;
    }

    public OperationIntentDTO() {
    }

    public static OperationIntentDTO fromModel(OperationIntent operationIntent) {
        return new OperationIntentDTO(
                operationIntent.getId(),
                operationIntent.getSymbol(),
                operationIntent.getCryptoAmount(),
                operationIntent.getCryptoPrice(),
                operationIntent.getOperationARSAmount(),
                operationIntent.getType(),
                operationIntent.getDateTime(),
                operationIntent.getStatus(),
                operationIntent.getTransaction(),
                SimpleUserDTO.fromModel(operationIntent.getUser())
        );
    }

    public static List<OperationIntentDTO> fromModelList(List<OperationIntent> operationIntents) {
        return operationIntents.stream().map(OperationIntentDTO::fromModel).toList();
    }
}
