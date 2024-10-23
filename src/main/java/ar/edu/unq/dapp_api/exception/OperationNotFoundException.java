package ar.edu.unq.dapp_api.exception;

public class OperationNotFoundException extends RuntimeException {
    public OperationNotFoundException() {
        super("Operation intent does not exist");
    }
}