package ar.edu.unq.dapp_api.exception;

public class NullActionException extends RuntimeException {
    public NullActionException() { super("Action cannot be null"); }
}