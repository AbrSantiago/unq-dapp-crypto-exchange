package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.exception.UserAlreadyExistsException;
import ar.edu.unq.dapp_api.webservice.dto.user.RegisterUserDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.UserDTO;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = new ArrayList<>();

        users.forEach(user -> userDTOs.add(UserDTO.fromUser(user)));

        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody RegisterUserDTO simpleUser) {
        User user = userService.registerUser(simpleUser);
        return ResponseEntity.ok(new UserDTO(user));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}