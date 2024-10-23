package ar.edu.unq.dapp_api.exception;

public class OperationInProcessException extends RuntimeException {
    public OperationInProcessException() {
        super("The operation is in process");
    }
}
