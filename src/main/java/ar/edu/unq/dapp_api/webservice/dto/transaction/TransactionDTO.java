package ar.edu.unq.dapp_api.webservice.dto.transaction;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import ar.edu.unq.dapp_api.webservice.dto.operationIntent.OperationIntentDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.SimpleUserDTO;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TransactionDTO {

    private final Long id;
    private final OperationIntentDTO operationIntent;
    private final SimpleUserDTO seller;
    private final SimpleUserDTO buyer;
    private final TransactionStatus status;
    private final LocalTime startTime;

    public TransactionDTO(Long id, OperationIntentDTO operationIntent, SimpleUserDTO seller, SimpleUserDTO buyer, TransactionStatus status, LocalTime startTime) {
        this.id = id;
        this.operationIntent = operationIntent;
        this.seller = seller;
        this.buyer = buyer;
        this.status = status;
        this.startTime = startTime;
    }

    public static TransactionDTO fromModel(Transaction transaction) {
        return new TransactionDTO(transaction.getId(), OperationIntentDTO.fromModel(transaction.getOperationIntent()), SimpleUserDTO.fromModel(transaction.getSeller()), SimpleUserDTO.fromModel(transaction.getBuyer()), transaction.getStatus(), transaction.getStartTime());
    }

}
