package ar.edu.unq.dapp_api.webservice.DTO;

import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.webservice.dto.user.UserDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void userDTOConstructorShouldInitializeFieldsCorrectly() {
        User user = new User("john.doe@example.com", "wallet123", "John", "Doe", "123 Main St", "password123", "123456789");
        UserDTO userDTO = new UserDTO(user);

        assertEquals("John", userDTO.getName());
        assertEquals("Doe", userDTO.getSurname());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals("123 Main St", userDTO.getAddress());
        assertEquals("password123", userDTO.getPassword());
        assertEquals("123456789", userDTO.getCvu());
        assertEquals("wallet123", userDTO.getWalletAddress());
        assertEquals(0, userDTO.getPointsObtained());
        assertEquals(0, userDTO.getOperationsPerformed());
    }

    @Test
    void toModelShouldReturnUserWithCorrectFields() {
        User user = new User("john.doe@example.com", "wallet123", "John", "Doe", "123 Main St", "password123", "123456789");
        UserDTO userDTO = new UserDTO(user);
        User model = userDTO.toModel();

        assertEquals("john.doe@example.com", model.getEmail());
        assertEquals("wallet123", model.getWalletAddress());
        assertEquals("John", model.getName());
        assertEquals("Doe", model.getSurname());
        assertEquals("123 Main St", model.getAddress());
        assertEquals("password123", model.getPassword());
        assertEquals("123456789", model.getCvu());
    }

    @Test
    void fromUserShouldReturnUserDTOWithCorrectFields() {
        User user = new User("john.doe@example.com", "wallet123", "John", "Doe", "123 Main St", "password123", "123456789");
        UserDTO userDTO = UserDTO.fromUser(user);

        assertEquals("John", userDTO.getName());
        assertEquals("Doe", userDTO.getSurname());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals("123 Main St", userDTO.getAddress());
        assertEquals("password123", userDTO.getPassword());
        assertEquals("123456789", userDTO.getCvu());
        assertEquals("wallet123", userDTO.getWalletAddress());
        assertEquals(0, userDTO.getPointsObtained());
        assertEquals(0, userDTO.getOperationsPerformed());
    }
}
