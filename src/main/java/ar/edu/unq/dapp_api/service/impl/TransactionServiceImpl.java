package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.repositories.TransactionRepository;
import ar.edu.unq.dapp_api.service.CryptoService;
import ar.edu.unq.dapp_api.service.OperationIntentService;
import ar.edu.unq.dapp_api.service.TransactionService;
import ar.edu.unq.dapp_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final OperationIntentService operationIntentService;
    private final CryptoService cryptoService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserService userService, OperationIntentService operationIntentService, CryptoService cryptoService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.operationIntentService = operationIntentService;
        this.cryptoService = cryptoService;
    }

    @Override
    public Transaction createTransaction(Long userId, Long operationIntentId) {

        User interestedUser = userService.getUserById(userId);
        OperationIntent operationIntent = operationIntentService.getOperationIntentById(operationIntentId);
        Float currentPrice = cryptoService.getCryptoCurrencyValue(operationIntent.getSymbol().toString()).getPrice();
        Transaction transaction = operationIntent.generateTransaction(interestedUser, currentPrice);
        transactionRepository.save(transaction);

        return transaction;
    }

}