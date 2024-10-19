package ar.edu.unq.dapp_api.webservice.dto.transaction;

import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
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

    public ProcessedTransactionDTO(Long id, CryptoSymbol symbol, BigDecimal cryptoAmount, BigDecimal cryptoPrice, String userName, String userSurname, int userAmountOfOperations, int userReputation, String sendDirection, TransactionStatus action) {
        if (id == null || symbol == null || cryptoAmount == null || cryptoPrice == null || userName == null || userSurname == null || sendDirection == null || action == null) {
            throw new IllegalArgumentException("None of the parameters can be null");
        }
        this.id = id;
        this.symbol = symbol;
        this.cryptoAmount = cryptoAmount;
        this.cryptoPrice = cryptoPrice;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userAmountOfOperations = userAmountOfOperations;
        this.userReputation = userReputation;
        this.sendDirection = sendDirection;
        this.action = action;
    }

    public static ProcessedTransactionDTO fromModel(Transaction transaction, User user) {
        if (transaction == null || user == null) {
            throw new IllegalArgumentException("Transaction and User cannot be null");
        }
        return new ProcessedTransactionDTO(
                transaction.getId(),
                transaction.getOperationIntent().getSymbol(),
                transaction.getOperationIntent().getCryptoAmount(),
                transaction.getOperationIntent().getCryptoPrice(),
                user.getName(),
                user.getSurname(),
                user.getOperationsPerformed(),
                user.getPointsObtained(),
                transaction.getDirection(),
                transaction.getStatus()
        );
    }
}