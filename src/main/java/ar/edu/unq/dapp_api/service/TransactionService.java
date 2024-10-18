package ar.edu.unq.dapp_api.service;

import ar.edu.unq.dapp_api.model.Transaction;

public interface TransactionService {

    Transaction createTransaction(Long userId, Long operationIntentId);

}
