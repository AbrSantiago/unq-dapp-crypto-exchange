package ar.edu.unq.dapp_api.webservice.dto.user;

import ar.edu.unq.dapp_api.model.User;
import lombok.Getter;

@Getter
public class SimpleUserDTO {

    private String name;
    private String surname;
    private String email;

    public SimpleUserDTO(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public SimpleUserDTO() {
    }

    public static SimpleUserDTO fromModel(User user) {
        return new SimpleUserDTO(user.getName(), user.getSurname(), user.getEmail());
    }

}
