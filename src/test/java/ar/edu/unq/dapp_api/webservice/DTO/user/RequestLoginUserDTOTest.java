package ar.edu.unq.dapp_api.webservice.DTO.user;

import ar.edu.unq.dapp_api.webservice.dto.user.RequestLoginUserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestLoginUserDTOTest {

    @Test
    void constructorInitializesFieldsCorrectly() {
        RequestLoginUserDTO dto = new RequestLoginUserDTO("john.doe@example.com", "Password1!");

        assertNotNull(dto);
        assertEquals("john.doe@example.com", dto.getEmail());
        assertEquals("Password1!", dto.getPassword());
    }

    @Test
    void defaultConstructorInitializesFieldsToNull() {
        RequestLoginUserDTO dto = new RequestLoginUserDTO();

        assertNotNull(dto);
        assertNull(dto.getEmail());
        assertNull(dto.getPassword());
    }

    @Test
    void setEmailUpdatesEmailField() {
        RequestLoginUserDTO dto = new RequestLoginUserDTO();
        dto.setEmail("john.doe@example.com");

        assertEquals("john.doe@example.com", dto.getEmail());
    }

    @Test
    void setPasswordUpdatesPasswordField() {
        RequestLoginUserDTO dto = new RequestLoginUserDTO();
        dto.setPassword("Password1!");

        assertEquals("Password1!", dto.getPassword());
    }

    @Test
    void constructorWithNullFields() {
        RequestLoginUserDTO dto = new RequestLoginUserDTO(null, null);

        assertNotNull(dto);
        assertNull(dto.getEmail());
        assertNull(dto.getPassword());
    }

    @Test
    void constructorWithEmptyFields() {
        RequestLoginUserDTO dto = new RequestLoginUserDTO("", "");

        assertNotNull(dto);
        assertEquals("", dto.getEmail());
        assertEquals("", dto.getPassword());
    }
}