package ar.edu.unq.dapp_api.webservice.DTO.user;

import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.webservice.dto.user.UserAccountDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserAccountDTOTest {

    @Test
    void fromUserCreatesDTOCorrectly() {
        User user = mock(User.class);
        when(user.getName()).thenReturn("John");
        when(user.getSurname()).thenReturn("Doe");
        when(user.getOperationsPerformed()).thenReturn(10);
        when(user.getReputation()).thenReturn(5);

        UserAccountDTO dto = UserAccountDTO.fromUser(user);

        assertNotNull(dto);
        assertEquals("John", dto.getName());
        assertEquals("Doe", dto.getSurname());
        assertEquals(10, dto.getAmountOfOperations());
        assertEquals(5, dto.getReputation());
    }

    @Test
    void fromUserHandlesNullUser() {
        User user = null;

        UserAccountDTO dto = UserAccountDTO.fromUser(user);

        assertNotNull(dto);
        assertNull(dto.getName());
        assertNull(dto.getSurname());
        assertEquals(0, dto.getAmountOfOperations());
        assertEquals(0, dto.getReputation());
    }

    @Test
    void constructorInitializesFieldsCorrectly() {
        UserAccountDTO dto = new UserAccountDTO("Jane", "Doe", 15, 8);

        assertNotNull(dto);
        assertEquals("Jane", dto.getName());
        assertEquals("Doe", dto.getSurname());
        assertEquals(15, dto.getAmountOfOperations());
        assertEquals(8, dto.getReputation());
    }
}