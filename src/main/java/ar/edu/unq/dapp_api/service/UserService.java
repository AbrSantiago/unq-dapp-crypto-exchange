package ar.edu.unq.dapp_api.service;

import ar.edu.unq.dapp_api.model.User;
import ar.edu.unq.dapp_api.webservice.dto.RegisterUserDTO;


import java.util.List;

public interface UserService {
    User registerUser(RegisterUserDTO registerUserDTO);
    List<User> getAllUsers();
    User getUserById(Long userId);
}
