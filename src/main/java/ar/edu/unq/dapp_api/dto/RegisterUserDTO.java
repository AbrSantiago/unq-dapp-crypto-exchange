package ar.edu.unq.dapp_api.dto;

import ar.edu.unq.dapp_api.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterUserDTO {
    @Size(min = 3, max = 30)
    @NotBlank
    private String name;

    @Size(min = 3, max = 30)
    @NotBlank
    private String surname;

    @Email(message = "Please provide a valid email address")
    @NotBlank
    private String email;

    @Size(min = 10, max = 30)
    @NotBlank
    private String address;

    @Size(min = 22, max = 22)
    @NotBlank
    private String cvu;

    @Size(min = 8, max = 8)
    @NotBlank
    private String walletAddress;

    @Size(min = 6, max = 30)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).+$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one special character")
    private String password;

    public User toModel() {
        return new User(email, walletAddress, name, surname, address, password, cvu);
    }
}