package ar.edu.unq.dapp_api.webservice.dto.operation_intent;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.webservice.dto.user.SimpleUserDTO;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OperationIntentDTO {

    private Long id;
    private CryptoSymbol symbol;
    private BigDecimal cryptoAmount;
    private BigDecimal cryptoPrice;
    private BigDecimal operationARSAmount;
    private IntentionType type;
    private LocalDateTime dateTime;
    private OperationStatus status;
    private Long transactionId;
    private SimpleUserDTO user;

    public static OperationIntentDTO fromModel(OperationIntent operationIntent) {
        return OperationIntentDTO.builder()
                .id(operationIntent.getId())
                .symbol(operationIntent.getSymbol())
                .cryptoAmount(operationIntent.getCryptoAmount())
                .cryptoPrice(operationIntent.getCryptoPrice())
                .operationARSAmount(operationIntent.getOperationARSAmount())
                .type(operationIntent.getType())
                .dateTime(operationIntent.getDateTime())
                .status(operationIntent.getStatus())
                .transactionId(operationIntent.getTransaction() != null ? operationIntent.getTransaction().getId() : null)
                .user(SimpleUserDTO.fromModel(operationIntent.getUser()))
                .build();
    }

    public static List<OperationIntentDTO> fromModelList(List<OperationIntent> operationIntents) {
        return operationIntents.stream().map(OperationIntentDTO::fromModel).toList();
    }
}