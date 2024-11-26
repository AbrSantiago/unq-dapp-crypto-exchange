package ar.edu.unq.dapp_api.service;

import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.webservice.dto.user.RegisterUserDTO;
import ar.edu.unq.dapp_api.webservice.dto.user.RequestLoginUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface UserService extends UserDetailsService {
    User registerUser(RegisterUserDTO registerUserDTO);
    User login(RequestLoginUserDTO loginUserDTO);
    List<User> getAllUsers();
    User getUserById(Long userId);
}
