package ar.edu.unq.dapp_api.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() { super("User already exists"); }
}