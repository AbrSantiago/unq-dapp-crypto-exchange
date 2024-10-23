package ar.edu.unq.dapp_api.exception;

public class TransactionConfirmedException extends RuntimeException {
    public TransactionConfirmedException() {
        super("Transaction was already confirmed");
    }
}
