package ar.edu.unq.dapp_api.service;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.webservice.dto.operation_intent.NewOperationIntentDTO;

import java.util.List;

public interface OperationIntentService {

    OperationIntent createOperationIntent(Long userId, NewOperationIntentDTO newOperationIntentDTO);

    List<OperationIntent> getActivesOperationIntents();

    OperationIntent getOperationIntentById(Long operationIntentId);

    List<OperationIntent> getActivesOperationIntentsFromUser(Long userId);
}
