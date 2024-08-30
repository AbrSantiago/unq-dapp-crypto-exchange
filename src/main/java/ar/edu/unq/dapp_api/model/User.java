package ar.edu.unq.dapp_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Please provide a valid email address")
    @NotBlank
    private String email;

    @Size(min = 8, max = 8)
    @NotBlank
    private String walletAddress;

    @Size(min = 3, max = 30)
    private String name;

    @Size(min = 3, max = 30)
    private String surname;

    @Size(min = 10, max = 30)
    private String address;

    @Size(min = 6, max = 30)
    private String password;

    @Size(min = 22, max = 22)
    private String cvu;

    @NotNull
    private int pointsObtained = 0;

    @NotNull
    private int operationsPerformed = 0;

    public User(String email, String walletAddress, String name, String surname, String address, String password, String cvu) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.walletAddress = walletAddress;
    }

    public User() {

    }

    public void discountPoints(int points) {
        pointsObtained = pointsObtained - Math.abs(points);
    }

    public void addPoints(int points) {
        pointsObtained = pointsObtained + Math.abs(points);
    }
}