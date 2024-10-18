package ar.edu.unq.dapp_api.webservice.dto.transaction;

import lombok.Getter;

@Getter
public class NewTransactionDTO {
    private final Long userId;
    private final Long operationIntentId;

    public NewTransactionDTO(Long userId, Long operationIntentId) {
        this.userId = userId;
        this.operationIntentId = operationIntentId;
    }

}
