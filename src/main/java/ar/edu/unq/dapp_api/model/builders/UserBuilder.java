package ar.edu.unq.dapp_api.model.builders;

import ar.edu.unq.dapp_api.model.User;
import java.util.ArrayList;

public class UserBuilder {
    private String name = "no name";
    private String surname = "no surname";
    private String email = "no@email.com";
    private String address = "no address 123";
    private String password = "no password";
    private String cvu = "1234567890123456789012";
    private String walletAddress = "12345678";
    private int pointsObtained = 0;
    private int operationsPerformed = 0;

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withCvu(String cvu) {
        this.cvu = cvu;
        return this;
    }

    public UserBuilder withWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
        return this;
    }

    public UserBuilder withPointsObtained(int pointsObtained) {
        this.pointsObtained = pointsObtained;
        return this;
    }

    public UserBuilder withOperationsPerformed(int operationsPerformed) {
        this.operationsPerformed = operationsPerformed;
        return this;
    }

    public User build() {
        User user = new User();
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setEmail(this.email);
        user.setAddress(this.address);
        user.setPassword(this.password);
        user.setCvu(this.cvu);
        user.setWalletAddress(this.walletAddress);
        user.setPointsObtained(this.pointsObtained);
        user.setOperationsPerformed(this.operationsPerformed);
        user.setUserOperationIntents(new ArrayList<>());
        return user;
    }
}