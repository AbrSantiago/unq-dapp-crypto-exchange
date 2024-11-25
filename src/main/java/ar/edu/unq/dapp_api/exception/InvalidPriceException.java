package ar.edu.unq.dapp_api.exception;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException(String s) {
        super(s);
    }
}
