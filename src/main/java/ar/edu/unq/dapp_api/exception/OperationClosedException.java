package ar.edu.unq.dapp_api.exception;

public class OperationClosedException extends RuntimeException {
    public OperationClosedException() { super("Operation has been already closed"); }
}