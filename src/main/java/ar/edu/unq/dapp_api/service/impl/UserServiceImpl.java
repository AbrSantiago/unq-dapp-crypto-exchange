package ar.edu.unq.dapp_api.service.impl;

import ar.edu.unq.dapp_api.exception.UserAlreadyExistsException;
import ar.edu.unq.dapp_api.exception.UserNotFoundException;
import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.repositories.UserRepository;
import ar.edu.unq.dapp_api.service.UserService;
import ar.edu.unq.dapp_api.webservice.dto.user.RegisterUserDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.RequestLoginUserDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Validator validator, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(RegisterUserDTO registerUserDTO) {
        Set<ConstraintViolation<RegisterUserDTO>> violations = validator.validate(registerUserDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        if (userRepository.existsByEmail(registerUserDTO.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        registerUserDTO.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        User user = registerUserDTO.toModel();

        return userRepository.save(user);
    }

    @Override
    public User login(RequestLoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.getEmail());
        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            throw new UserNotFoundException();
        }


        return user;
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
                .roles("USER")
                .build();
    }

}
