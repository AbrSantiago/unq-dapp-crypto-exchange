package ar.edu.unq.dapp_api.webservice.dto.operationIntent;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.webservice.dto.transaction.TransactionDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.SimpleUserDTO;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
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

    public OperationIntentDTO(Long id, CryptoSymbol symbol, BigDecimal cryptoAmount, BigDecimal cryptoPrice, BigDecimal operationARSAmount, IntentionType type, LocalDateTime dateTime, OperationStatus status, Long transactionId, SimpleUserDTO user) {
        this.id = id;
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationARSAmount = operationARSAmount;
        this.type = type;
        this.dateTime = dateTime;
        this.status = status;
        this.transactionId = transactionId;
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
                operationIntent.getTransaction() != null ? operationIntent.getTransaction().getId() : null,
                SimpleUserDTO.fromModel(operationIntent.getUser())
        );
    }


    public static List<OperationIntentDTO> fromModelList(List<OperationIntent> operationIntents) {
        return operationIntents.stream().map(OperationIntentDTO::fromModel).toList();
    }
}
