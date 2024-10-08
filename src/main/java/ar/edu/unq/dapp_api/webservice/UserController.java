package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.dto.RegisterUserDTO;
import ar.edu.unq.dapp_api.exception.DuplicateResourceException;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody RegisterUserDTO simpleUser) {
        User user = new User(
                simpleUser.getEmail(),
                simpleUser.getWalletAddress(),
                simpleUser.getName(),
                simpleUser.getSurname(),
                simpleUser.getAddress(),
                simpleUser.getPassword(),
                simpleUser.getCvu()
        );
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("A user with the same email, CVU, or wallet address already exists.");
        }
    }
}