package ar.edu.unq.dapp_api.model.builders;

import ar.edu.unq.dapp_api.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserBuilderTest {
    @Test
    public void testUserBuilder() {
        User user = new UserBuilder().build();
        assertEquals("no name", user.getName());
        assertEquals("no surname", user.getSurname());
        assertEquals("no@email.com", user.getEmail());
        assertEquals("no address 123", user.getAddress());
        assertEquals("no password", user.getPassword());
        assertEquals("1234567890123456789012", user.getCvu());
        assertEquals("12345678", user.getWalletAddress());
        assertEquals(0, user.getPointsObtained());
        assertEquals(0, user.getOperationsPerformed());
    }

    @Test
    public void testUserNameBuilder() {
        User user = new UserBuilder().withName("Juan").build();
        assertEquals("Juan", user.getName());
    }

    @Test
    public void testUserSurnameBuilder() {
        User user = new UserBuilder().withSurname("Doe").build();
        assertEquals("Doe", user.getSurname());
    }

    @Test
    public void testUserEmailBuilder() {
        User user = new UserBuilder().withEmail("asd@gmail.com").build();
        assertEquals("asd@gmail.com", user.getEmail());
    }

    @Test
    public void testUserAddressBuilder() {
        User user = new UserBuilder().withAddress("street 123").build();
        assertEquals("street 123", user.getAddress());
    }

    @Test
    public void testUserPasswordBuilder() {
        User user = new UserBuilder().withPassword("123132123").build();
        assertEquals("123132123", user.getPassword());
    }

    @Test
    public void testUserCvuBuilder() {
        User user = new UserBuilder().withCvu("1234512345123451234512").build();
        assertEquals("1234512345123451234512", user.getCvu());
    }

    @Test
    public void testUserWalletAddressBuilder() {
        User user = new UserBuilder().withWalletAddress("12341234").build();
        assertEquals("12341234", user.getWalletAddress());
    }

    @Test
    public void testUserPointsObtainedBuilder() {
        User user = new UserBuilder().withPointsObtained(2).build();
        assertEquals(2, user.getPointsObtained());
    }

    @Test
    public void testUserOperationsPerformedBuilder() {
        User user = new UserBuilder().withOperationsPerformed(2).build();
        assertEquals(2, user.getOperationsPerformed());
    }
}