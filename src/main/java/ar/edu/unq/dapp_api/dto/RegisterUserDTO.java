package ar.edu.unq.dapp_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @Size(min = 8)
    @NotBlank
    private String password;
}
