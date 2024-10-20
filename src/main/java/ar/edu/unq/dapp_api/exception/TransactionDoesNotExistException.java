package ar.edu.unq.dapp_api.exception;

public class TransactionDoesNotExistException extends RuntimeException {
    public TransactionDoesNotExistException(Long transactionId) {
        super("Transaction with id " + transactionId + " not found");
    }
}