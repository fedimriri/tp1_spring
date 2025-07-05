package com.tekup.tp1.controllers;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tekup.tp1.entites.User;
import com.tekup.tp1.entites.Task;
import com.tekup.tp1.exception.UserNotFoundException;
import com.tekup.tp1.services.IUserService;

@RestController
@RequestMapping("/api/v0/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    private final IUserService userService;
    
    public UserController(IUserService userService) {
        this.userService = userService;
    }
    
    // CRUD Operations

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Advanced Methods

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByNameOrEmail(
            @RequestParam String searchTerm) {
        List<User> users = userService.searchUsersByNameOrEmail(searchTerm);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-role/{roleId}")
    public ResponseEntity<List<User>> getUsersByRole(
            @PathVariable Long roleId) {
        List<User> users = userService.getUsersByRole(roleId);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Task>> getTasksAssignedToUser(
            @PathVariable Long userId) {
        List<Task> tasks = userService.getTasksAssignedToUser(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/exists-by-email")
    public ResponseEntity<Boolean> existsByEmail(
            @RequestParam String email) {
        Boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}
