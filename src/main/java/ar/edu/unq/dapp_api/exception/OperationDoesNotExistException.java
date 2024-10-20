package ar.edu.unq.dapp_api.exception;

public class OperationDoesNotExistException extends RuntimeException {
    public OperationDoesNotExistException() {
        super("Operation intent does not exist");
    }
}