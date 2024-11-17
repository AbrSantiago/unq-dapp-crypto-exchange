package ar.edu.unq.dapp_api.repositories;

import ar.edu.unq.dapp_api.archunit.ExcludeFromArchitectureCheck;
import ar.edu.unq.dapp_api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@ExcludeFromArchitectureCheck
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testUserPersistence() {
        User user = new User("test@example.com", "12345678", "John", "Doe", "123 Main St", "Password@123", "1234567890123456789012");
        userRepository.save(user);

        // Verifica que el usuario se haya guardado y tenga un ID asignado
        assertThat(user.getId()).isNotNull();

        // Verifica que el usuario pueda ser recuperado por su ID
        User foundUser = userRepository.findById(user.getId()).orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
    }
}
