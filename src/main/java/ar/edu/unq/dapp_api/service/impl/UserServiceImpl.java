package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.exception.UserAlreadyExistsException;
import ar.edu.unq.dapp_api.exception.UserNotFoundException;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.repositories.UserRepository;
import ar.edu.unq.dapp_api.service.UserService;
import ar.edu.unq.dapp_api.webservice.dto.user.RegisterUserDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.RequestLoginUserDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.UserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Validator validator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(RegisterUserDTO registerUserDTO) {
        // Validar el DTO
        Set<ConstraintViolation<RegisterUserDTO>> violations = validator.validate(registerUserDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // Verificar si el email ya existe
        if (userRepository.existsByEmail(registerUserDTO.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        // Codificar contraseña y convertir DTO a modelo
        registerUserDTO.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        User user = registerUserDTO.toModel();

        // Guardar el usuario
        return userRepository.save(user);
    }

    @Override
    public UserDTO login(RequestLoginUserDTO loginUserDTO) {
        // Buscar al usuario por email
        User user = userRepository.findByEmail(loginUserDTO.getEmail());
        if (user == null) {
            throw new UserNotFoundException();
        }

        // Verificar la contraseña
        if (!passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            throw new UserNotFoundException();
        }


        return UserDTO.fromUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER") // Cambia esto si tienes roles específicos
                .build();
    }

}
