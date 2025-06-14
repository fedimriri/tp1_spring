package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.User;
import com.tekup.tp1.entites.Role;
import com.tekup.tp1.entites.Task;
import com.tekup.tp1.exception.UserNotFoundException;

public interface IUserService {

    // CRUD operations
    User createUser(User user);
    User updateUser(Long id, User user) throws UserNotFoundException;
    void deleteUser(Long id) throws UserNotFoundException;

    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    Boolean existsByEmail(String email);

    // Advanced methods
    List<User> searchUsersByNameOrEmail(String searchTerm);
    List<User> getUsersByRole(Long roleId);
    List<Task> getTasksAssignedToUser(Long userId);

}
