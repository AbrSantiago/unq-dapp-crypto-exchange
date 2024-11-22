package ar.edu.unq.dapp_api.config;

import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;

    public DataInitializer(PasswordEncoder passwordEncoder, @Value("${app.default.user.password}") String defaultPassword) {
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
    }

    @Bean
    public ApplicationRunner initializer(UserRepository userRepository) {
        return _ -> {
            String encodedPassword = passwordEncoder.encode(defaultPassword);

            User user1 = new User("juan@mail.com", "0x123456", "Juan", "Perez", "Calle Falsa 123", encodedPassword, "1234567890123456789012");
            User user2 = new User("maria@mail.com", "0x567890", "Maria", "Gomez", "Avenida Siempre Viva 742", encodedPassword, "2345678901234567890123");
            User user3 = new User("pedro@mail.com", "0x910111", "Pedro", "Lopez", "Calle Real 456", encodedPassword, "3456789012345678901234");
            User user4 = new User("ana@mail.com", "0x112131", "Ana", "Martinez", "Calle Falsa 789", encodedPassword, "4567890123456789012345");
            User user5 = new User("luis@mail.com", "0x314151", "Luis", "Garcia", "Avenida Central 123", encodedPassword, "5678901234567890123456");
            User user6 = new User("laura@mail.com", "0x516171", "Laura", "Rodriguez", "Calle Norte 456", encodedPassword, "6789012345678901234567");
            User user7 = new User("carlos@mail.com", "0x718191", "Carlos", "Fernandez", "Calle Sur 789", encodedPassword, "7890123456789012345678");
            User user8 = new User("marta@mail.com", "0x920111", "Marta", "Sanchez", "Avenida Oeste 123", encodedPassword, "8901234567890123456789");
            User user9 = new User("jose@mail.com", "0x122131", "Jose", "Ramirez", "Calle Este 456", encodedPassword, "9012345678901234567890");
            User user10 = new User("sofia@mail.com", "0x324151", "Sofia", "Torres", "Avenida Principal 789", encodedPassword, "0123456789012345678901");

            userRepository.saveAll(List.of(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10));
        };
    }
}
