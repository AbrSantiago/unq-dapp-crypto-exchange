package ar.edu.unq.dapp_api.webservice.dto.transaction;

import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProcessedTransactionDTO {

    private final Long id;
    private final CryptoSymbol symbol;
    private final BigDecimal cryptoAmount;
    private final BigDecimal cryptoPrice;
    private final String userName;
    private final String userSurname;
    private final int userAmountOfOperations;
    private final int userReputation;
    private final String sendDirection;
    private final TransactionStatus action;

    public static ProcessedTransactionDTO fromModel(Transaction transaction, User user) {
        if (transaction == null || user == null) {
            throw new IllegalArgumentException("Transaction and User cannot be null");
        }
        return ProcessedTransactionDTO.builder()
                .id(transaction.getId())
                .symbol(transaction.getOperationIntent().getSymbol())
                .cryptoAmount(transaction.getOperationIntent().getCryptoAmount())
                .cryptoPrice(transaction.getOperationIntent().getCryptoPrice())
                .userName(user.getName())
                .userSurname(user.getSurname())
                .userAmountOfOperations(user.getOperationsPerformed())
                .userReputation(user.getPointsObtained())
                .sendDirection(transaction.getDirection())
                .action(transaction.getStatus())
                .build();
    }
}