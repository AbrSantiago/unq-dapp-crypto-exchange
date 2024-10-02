package ar.edu.unq.dapp_api.webservice.dto;

import ar.edu.unq.dapp_api.model.User;
import lombok.Getter;

@Getter
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String password;
    private String cvu;
    private String walletAddress;
    private int pointsObtained;
    private int operationsPerformed;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.password = user.getPassword();
        this.cvu = user.getCvu();
        this.walletAddress = user.getWalletAddress();
        this.pointsObtained = user.getPointsObtained();
        this.operationsPerformed = user.getOperationsPerformed();
    }

    public User toModel() {
        return new User(email, walletAddress, name, surname, address, password, cvu);
    }

    public static UserDTO fromUser(User user) {
        return new UserDTO(user);
    }
}