package ar.edu.unq.dapp_api.exception;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException() {
        super("User does not exist");
    }
}