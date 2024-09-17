package ar.edu.unq.dapp_api.exception;

public class TransactionCompletedException extends RuntimeException {
    public TransactionCompletedException(String message) {
        super(message);
    }
}