package ar.edu.unq.dapp_api.webservice;

import ar.edu.unq.dapp_api.exception.UserAlreadyExistsException;
import ar.edu.unq.dapp_api.exception.UserNotFoundException;
import ar.edu.unq.dapp_api.config.util.JwtUtil;
import ar.edu.unq.dapp_api.webservice.dto.user.RegisterUserDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.RequestLoginUserDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.UserDTO;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "User services", description = "Manage users")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = new ArrayList<>();

        users.forEach(user -> userDTOs.add(UserDTO.fromUser(user)));

        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "200", description = "User created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    public ResponseEntity<Object> createUser(@Valid @RequestBody RegisterUserDTO simpleUser) {
        try {
            User user = userService.registerUser(simpleUser);
            return ResponseEntity.ok(new UserDTO(user));
        } catch (ConstraintViolationException e) {
            List<String> errorMessages = e.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error creating user: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/auth/login")
    @Operation(summary = "Login a user")
    @ApiResponse(responseCode = "200", description = "User logged in successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    public ResponseEntity<String> login(@RequestBody RequestLoginUserDTO requestLoginUserDTO) {
        try {
            User user = userService.login(requestLoginUserDTO);
            String token = jwtUtil.generateToken(user.getEmail());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}