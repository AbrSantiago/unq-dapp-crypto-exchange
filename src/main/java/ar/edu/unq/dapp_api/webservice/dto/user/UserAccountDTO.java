package ar.edu.unq.dapp_api.webservice.dto.user;

import ar.edu.unq.dapp_api.model.User;
import lombok.Getter;

@Getter
public class UserAccountDTO {
    private final String name;
    private final String surname;
    private final int amountOfOperations;
    private final int reputation;

    public UserAccountDTO(String name, String surname, int amountOfOperations, int reputation) {
        this.name = name;
        this.surname = surname;
        this.amountOfOperations = amountOfOperations;
        this.reputation = reputation;
    }

    public static UserAccountDTO fromUser(User user) {
        if (user == null) {
            return new UserAccountDTO(null, null, 0, 0);
        }
        return new UserAccountDTO(user.getName(), user.getSurname(), user.getOperationsPerformed(), user.getReputation());
    }
}