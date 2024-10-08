package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.exception.InvalidTransactionStateException;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 30)
    @NotBlank
    private String name;

    @Size(min = 3, max = 30)
    @NotBlank
    private String surname;

    @Email(message = "Please provide a valid email address")
    @NotBlank
    @Column(unique = true)
    private String email;

    @Size(min = 10, max = 30)
    @NotBlank
    private String address;

    @Size(min = 6, max = 30)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).+$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one special character")
    private String password;

    @Size(min = 22, max = 22)
    @NotBlank
    @Column(unique = true)
    private String cvu;

    @Size(min = 8, max = 8)
    @NotBlank
    @Column(unique = true)
    private String walletAddress;

    @NotNull
    private int pointsObtained;

    @NotNull
    private int operationsPerformed;

    @Transient
    private List<OperationIntent> userOperationIntents;

    public User(String email, String walletAddress, String name, String surname, String address, String password, String cvu) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.walletAddress = walletAddress;
        this.pointsObtained = 0;
        this.operationsPerformed = 0;
        this.userOperationIntents = new ArrayList<>();
    }

    public User() {
        this.userOperationIntents = new ArrayList<>();
    }

    public OperationIntent publishOperationIntent(CryptoSymbol symbol, Long cryptoAmount, Long cryptoPrice, Long operationARSAmount, IntentionType type) {
        OperationIntent operationIntent = new OperationIntent(symbol, cryptoAmount, cryptoPrice, operationARSAmount, this, type);
        userOperationIntents.add(operationIntent);
        return operationIntent;
    }

    public void notifySentTransaction(Transaction transaction) {
        transaction.completeTransfer();
    }

    public void notifyReceivedTransaction(Transaction transaction) {
        transaction.confirmReception();
    }

    public void cancelTransaction(Transaction transaction) {
        if (transaction.getStatus().equals(TransactionStatus.CONFIRMED) || transaction.getStatus().equals(TransactionStatus.TRANSFERRED)) {
            throw new InvalidTransactionStateException("Cannot cancel a transaction that is already confirmed or transferred");
        }
        transaction.cancelTransaction();
        this.discountPoints(transaction.cancelByUserPoints());
    }

    public void discountPoints(int points) {
        if (this.pointsObtained - points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        this.pointsObtained -= points;
    }

    public void addPoints(int points) {
        pointsObtained += points;
    }
}