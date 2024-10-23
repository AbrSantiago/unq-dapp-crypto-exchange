package ar.edu.unq.dapp_api.exception;

import ar.edu.unq.dapp_api.model.enums.TransactionStatus;

public class InvalidActionException extends RuntimeException {
    public InvalidActionException(TransactionStatus action) {
        super(action + "is an invalid action");
    }
}