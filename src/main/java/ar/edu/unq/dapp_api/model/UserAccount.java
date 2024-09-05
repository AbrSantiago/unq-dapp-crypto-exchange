package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.ArrayList;


@Getter
public class UserAccount {

    @NotNull
    private int pointsObtained = 0;

    @NotNull
    private int operationsPerformed = 0;

    @Getter
    private final ArrayList<OperationIntent> operationIntents = new ArrayList<>();

    private final User user;

    public UserAccount(User user) {
        this.user = user;
    }

    public void discountPoints(int points) {
        pointsObtained -= points;
    }

    public void addPoints(int points) {
        pointsObtained += points;
    }

    public void addOperation() {
        operationsPerformed++;
    }

    public void publishOperationIntent(CryptoSymbol symbol, Long cryptoAmount, Long cryptoPrice, Long operationARSAmount, IntentionType type) {
        OperationIntent operationIntent = new OperationIntent(symbol, cryptoAmount, cryptoPrice, operationARSAmount, this, type);
        operationIntents.add(operationIntent);
    }


}
