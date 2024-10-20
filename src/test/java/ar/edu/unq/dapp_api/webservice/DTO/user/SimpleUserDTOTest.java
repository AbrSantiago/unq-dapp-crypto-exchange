package ar.edu.unq.dapp_api.webservice.DTO.user;

import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.webservice.dto.user.SimpleUserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleUserDTOTest {

    @Test
    void fromModelCreatesDTOCorrectly() {
        User user = mock(User.class);
        when(user.getName()).thenReturn("John");
        when(user.getSurname()).thenReturn("Doe");
        when(user.getEmail()).thenReturn("john.doe@example.com");

        SimpleUserDTO dto = SimpleUserDTO.fromModel(user);

        assertNotNull(dto);
        assertEquals("John", dto.getName());
        assertEquals("Doe", dto.getSurname());
        assertEquals("john.doe@example.com", dto.getEmail());
    }

    @Test
    void fromModelHandlesNullUser() {
        User user = null;

        SimpleUserDTO dto = SimpleUserDTO.fromModel(user);

        assertNotNull(dto);
        assertNull(dto.getName());
        assertNull(dto.getSurname());
        assertNull(dto.getEmail());
    }

    @Test
    void constructorInitializesFieldsCorrectly() {
        SimpleUserDTO dto = new SimpleUserDTO("Jane", "Doe", "jane.doe@example.com");

        assertNotNull(dto);
        assertEquals("Jane", dto.getName());
        assertEquals("Doe", dto.getSurname());
        assertEquals("jane.doe@example.com", dto.getEmail());
    }
}