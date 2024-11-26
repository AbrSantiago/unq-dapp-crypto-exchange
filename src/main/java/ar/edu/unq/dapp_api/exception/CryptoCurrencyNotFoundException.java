package ar.edu.unq.dapp_api.exception;

public class CryptoCurrencyNotFoundException  extends RuntimeException {
    public CryptoCurrencyNotFoundException() {
        super("Crypto currency not found");
    }
}
