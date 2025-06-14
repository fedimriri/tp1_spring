package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.User;

public interface IUserService {

    User createUser(User user);
    User updateUser(Long id,User user);
    void deleteUser(Long id);

    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    Boolean existsByEmail(String email);
    
}
