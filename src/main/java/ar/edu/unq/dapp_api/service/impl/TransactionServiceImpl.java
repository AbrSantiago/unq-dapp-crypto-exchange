package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.exception.*;
import ar.edu.unq.dapp_api.model.OperationIntent;
import ar.edu.unq.dapp_api.model.Transaction;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.model.enums.OperationStatus;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import ar.edu.unq.dapp_api.repositories.TransactionRepository;
import ar.edu.unq.dapp_api.service.CryptoService;
import ar.edu.unq.dapp_api.service.OperationIntentService;
import ar.edu.unq.dapp_api.service.TransactionService;
import ar.edu.unq.dapp_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
        if (interestedUser == null) {
            throw new UserNotFoundException();
        }
        OperationIntent operationIntent = operationIntentService.getOperationIntentById(operationIntentId);
        if (operationIntent == null) {
            throw new OperationNotFoundException();
        } else if (operationIntent.getStatus().equals(OperationStatus.CLOSED)) {
            throw new OperationClosedException();
        } else if (operationIntent.getStatus().equals(OperationStatus.IN_PROCESS)) {
            throw new OperationInProcessException();
        }
        BigDecimal currentPrice = cryptoService.getCryptoCurrencyValue(operationIntent.getSymbol().toString()).getPrice();
        Transaction transaction = operationIntent.generateTransaction(interestedUser, currentPrice);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public Transaction processTransaction(Long transactionId, Long userId, TransactionStatus action) {
        if (action == null) {
            throw new NullActionException();
        }
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!transaction.getSeller().equals(user) && !transaction.getBuyer().equals(user)) {
            throw new UnauthorizedUserException();
        }
        switch (action) {
            case TRANSFERRED -> user.notifySentTransaction(transaction);
            case CONFIRMED -> user.notifyReceivedTransaction(transaction);
            case CANCELLED -> user.cancelTransaction(transaction);
            default -> throw new InvalidActionException(action);
        }
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public int getConfirmedTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findConfirmedTransactionsByDateRange(startDate, endDate);
    }
}