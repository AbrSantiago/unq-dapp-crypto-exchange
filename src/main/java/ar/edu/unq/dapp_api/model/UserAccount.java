package ar.edu.unq.dapp_api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class UserAccount {

    @NotNull
    private int pointsObtained = 0;

    @NotNull
    private int operationsPerformed = 0;

    public void discountPoints(int points) {
        pointsObtained -= points;
    }

    public void addPoints(int points) {
        pointsObtained += points;
    }

    public void addOperation() {
        operationsPerformed++;
    }
}
