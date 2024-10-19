package ar.edu.unq.dapp_api.webservice.dto.user;

import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.webservice.dto.operationIntent.OperationIntentDTO;
import ar.edu.unq.dapp_api.webservice.dto.transaction.TransactionDTO;
import lombok.Getter;

import java.util.List;

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
    private List<OperationIntentDTO> operationIntents;
    private List<TransactionDTO> sellTransactions;
    private List<TransactionDTO> buyTransactions;

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
        this.operationIntents = user.getUserOperationIntents().stream()
                .map(OperationIntentDTO::fromModel)
                .toList();

        this.sellTransactions = user.getSellerTransactions().stream()
                .map(TransactionDTO::fromModel)
                .toList();

        this.buyTransactions = user.getBuyerTransactions().stream()
                .map(TransactionDTO::fromModel)
                .toList();
    }

    public User toModel() {
        return new User(email, walletAddress, name, surname, address, password, cvu);
    }

    public static UserDTO fromUser(User user) {
        return new UserDTO(user);
    }
}