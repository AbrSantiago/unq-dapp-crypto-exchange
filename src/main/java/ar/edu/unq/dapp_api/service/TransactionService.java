package ar.edu.unq.dapp_api.service;

import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import java.time.LocalDateTime;

public interface TransactionService {

    Transaction createTransaction(Long userId, Long operationIntentId);

    Transaction processTransaction(Long transactionId, Long userId, TransactionStatus action);

    int getConfirmedTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}