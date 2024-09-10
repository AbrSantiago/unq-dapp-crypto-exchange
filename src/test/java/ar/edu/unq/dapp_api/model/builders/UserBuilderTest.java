package ar.edu.unq.dapp_api.model.builders;

import ar.edu.unq.dapp_api.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserBuilderTest {
    @Test
    void testUserBuilder() {
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
    void testUserNameBuilder() {
        User user = new UserBuilder().withName("Juan").build();
        assertEquals("Juan", user.getName());
    }

    @Test
    void testUserSurnameBuilder() {
        User user = new UserBuilder().withSurname("Doe").build();
        assertEquals("Doe", user.getSurname());
    }

    @Test
    void testUserEmailBuilder() {
        User user = new UserBuilder().withEmail("asd@gmail.com").build();
        assertEquals("asd@gmail.com", user.getEmail());
    }

    @Test
    void testUserAddressBuilder() {
        User user = new UserBuilder().withAddress("street 123").build();
        assertEquals("street 123", user.getAddress());
    }

    @Test
    void testUserPasswordBuilder() {
        User user = new UserBuilder().withPassword("123132123").build();
        assertEquals("123132123", user.getPassword());
    }

    @Test
    void testUserCvuBuilder() {
        User user = new UserBuilder().withCvu("1234512345123451234512").build();
        assertEquals("1234512345123451234512", user.getCvu());
    }

    @Test
    void testUserWalletAddressBuilder() {
        User user = new UserBuilder().withWalletAddress("12341234").build();
        assertEquals("12341234", user.getWalletAddress());
    }

    @Test
    void testUserPointsObtainedBuilder() {
        User user = new UserBuilder().withPointsObtained(2).build();
        assertEquals(2, user.getPointsObtained());
    }

    @Test
    void testUserOperationsPerformedBuilder() {
        User user = new UserBuilder().withOperationsPerformed(2).build();
        assertEquals(2, user.getOperationsPerformed());
    }
}