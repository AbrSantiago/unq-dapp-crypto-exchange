package ar.edu.unq.dapp_api.model;

import ar.edu.unq.dapp_api.exception.InvalidTransactionStateException;
import ar.edu.unq.dapp_api.model.enums.CryptoSymbol;
import ar.edu.unq.dapp_api.model.enums.IntentionType;
import ar.edu.unq.dapp_api.model.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OperationIntent> userOperationIntents = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> sellerTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> buyerTransactions = new ArrayList<>();


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

    public OperationIntent publishOperationIntent(CryptoSymbol symbol, BigDecimal cryptoAmount, BigDecimal cryptoPrice, BigDecimal operationARSAmount, IntentionType type) {
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
        if (transaction.getStatus().equals(TransactionStatus.CANCELLED)) {
            throw new InvalidTransactionStateException("Transaction already canceled");
        }
        transaction.cancelTransaction();
        this.discountPoints(transaction.cancelByUserPoints());
    }

    public void discountPoints(int points) {
        this.setPointsObtained(Math.max(this.pointsObtained - points, 0));
    }

    public void addPoints(int points) {
        pointsObtained += points;
    }

    public void addOperation() {
        operationsPerformed++;
    }

    public int getReputation() {
        if (operationsPerformed == 0 || pointsObtained == 0) {
            return 0;
        }
        else return pointsObtained / operationsPerformed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}