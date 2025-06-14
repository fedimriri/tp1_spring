package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tekup.tp1.entites.User;
import com.tekup.tp1.entites.Role;
import com.tekup.tp1.entites.Task;
import com.tekup.tp1.exception.UserNotFoundException;
import com.tekup.tp1.exception.RoleNotFoundException;
import com.tekup.tp1.repositories.UserRepository;
import com.tekup.tp1.repositories.RoleRepository;
import com.tekup.tp1.repositories.TaskRepository;

@Service
public class UserServiceImpl implements IUserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private TaskRepository taskRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) throws UserNotFoundException {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            return userRepository.save(existingUser);
        } else {
            throw new UserNotFoundException("User not found with Id: " + id);
        }
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with Id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> searchUsersByNameOrEmail(String searchTerm) {
        return userRepository.findByUsernameContainingOrEmailContaining(searchTerm);
    }

    @Override
    public List<User> getUsersByRole(Long roleId) {
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (roleOpt.isPresent()) {
            return userRepository.findByRole(roleOpt.get());
        }
        return List.of(); 
    }

    @Override
    public List<Task> getTasksAssignedToUser(Long userId) {
        return taskRepository.findTasksAssignedToUser(userId);
    }

}
