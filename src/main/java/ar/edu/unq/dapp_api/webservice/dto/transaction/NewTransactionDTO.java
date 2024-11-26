package ar.edu.unq.dapp_api.webservice.dto.transaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewTransactionDTO {
    private Long userId;
    private Long operationIntentId;

    public NewTransactionDTO() {
    }

    public NewTransactionDTO(Long userId, Long operationIntentId) {
        this.userId = userId;
        this.operationIntentId = operationIntentId;
    }

}