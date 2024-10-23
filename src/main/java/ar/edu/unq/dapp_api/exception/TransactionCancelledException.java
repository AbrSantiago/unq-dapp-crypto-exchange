package ar.edu.unq.dapp_api.exception;

public class TransactionCancelledException extends RuntimeException {
    public TransactionCancelledException() {
        super("Transaction was cancelled");
    }
}
