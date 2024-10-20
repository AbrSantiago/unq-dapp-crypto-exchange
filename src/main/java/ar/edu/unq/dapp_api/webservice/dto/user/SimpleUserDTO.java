package ar.edu.unq.dapp_api.webservice.dto.user;

import ar.edu.unq.dapp_api.model.User;
import lombok.Getter;

@Getter
public class SimpleUserDTO {

    private final String name;
    private final String surname;
    private final String email;

    public SimpleUserDTO(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public static SimpleUserDTO fromModel(User user) {
        if (user == null) {
            return new SimpleUserDTO(null, null, null);
        }
        return new SimpleUserDTO(user.getName(), user.getSurname(), user.getEmail());
    }
}