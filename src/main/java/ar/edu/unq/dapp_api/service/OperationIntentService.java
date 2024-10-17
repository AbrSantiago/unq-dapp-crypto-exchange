package ar.edu.unq.dapp_api.service;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.webservice.dto.NewOperationIntentDTO;

public interface OperationIntentService {

    OperationIntent createOperationIntent(Long userId, NewOperationIntentDTO newOperationIntentDTO);

}
