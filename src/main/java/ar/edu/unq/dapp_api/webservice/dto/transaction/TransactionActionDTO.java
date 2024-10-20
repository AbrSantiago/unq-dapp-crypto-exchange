package ar.edu.unq.dapp_api.webservice.dto.transaction;

import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionActionDTO {
    private Long userId;
    private TransactionStatus action;
}
