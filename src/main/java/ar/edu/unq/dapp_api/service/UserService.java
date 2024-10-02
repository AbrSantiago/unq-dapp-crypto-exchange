package ar.edu.unq.dapp_api.service;

import ar.edu.unq.dapp_api.model.User;


import java.util.List;

public interface UserService {
    User registerUser(User user);
    List<User> getAllUsers();
}
