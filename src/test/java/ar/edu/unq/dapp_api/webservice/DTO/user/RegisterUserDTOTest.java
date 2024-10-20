package ar.edu.unq.dapp_api.webservice.DTO.user;

import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.webservice.dto.user.RegisterUserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterUserDTOTest {

    @Test
    void toModelCreatesUserCorrectly() {
        RegisterUserDTO dto = new RegisterUserDTO("john.doe@example.com", "wallet1234", "John", "Doe", "123 Main St", "Password1!", "1234567890123456789012");

        User user = dto.toModel();

        assertNotNull(user);
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("wallet1234", user.getWalletAddress());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("Password1!", user.getPassword());
        assertEquals("1234567890123456789012", user.getCvu());
    }

    @Test
    void constructorInitializesFieldsCorrectly() {
        RegisterUserDTO dto = new RegisterUserDTO("john.doe@example.com", "wallet1234", "John", "Doe", "123 Main St", "Password1!", "1234567890123456789012");

        assertNotNull(dto);
        assertEquals("john.doe@example.com", dto.getEmail());
        assertEquals("wallet1234", dto.getWalletAddress());
        assertEquals("John", dto.getName());
        assertEquals("Doe", dto.getSurname());
        assertEquals("123 Main St", dto.getAddress());
        assertEquals("Password1!", dto.getPassword());
        assertEquals("1234567890123456789012", dto.getCvu());
    }

    @Test
    void defaultConstructorInitializesFieldsToNull() {
        RegisterUserDTO dto = new RegisterUserDTO();

        assertNotNull(dto);
        assertNull(dto.getEmail());
        assertNull(dto.getWalletAddress());
        assertNull(dto.getName());
        assertNull(dto.getSurname());
        assertNull(dto.getAddress());
        assertNull(dto.getPassword());
        assertNull(dto.getCvu());
    }
}