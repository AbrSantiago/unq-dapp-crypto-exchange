package ar.edu.unq.dapp_api.exception;

public class OperationExpiredException extends RuntimeException {
    public OperationExpiredException() {
        super("Operation intent expired");
    }
}