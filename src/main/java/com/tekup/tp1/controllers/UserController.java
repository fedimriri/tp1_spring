package com.tekup.tp1.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tekup.tp1.entites.User;
import com.tekup.tp1.entites.Task;
import com.tekup.tp1.exception.UserNotFoundException;
import com.tekup.tp1.services.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "User Management", description = "APIs for managing users including CRUD operations and advanced methods")
public class UserController {
    
    private final IUserService userService;
    
    public UserController(IUserService userService) {
        this.userService = userService;
    }
    
    // CRUD Operations

    @Operation(
        summary = "Get all users",
        description = "Retrieve a list of all users in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all users",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @Operation(
        summary = "Get user by ID",
        description = "Retrieve a specific user by their unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid user ID format")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "Unique identifier of the user", required = true, example = "1")
            @PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(
        summary = "Create a new user",
        description = "Create a new user with validation. Username must be 3-50 characters, email must be valid format."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Invalid user data or validation errors"),
        @ApiResponse(responseCode = "409", description = "User with email already exists")
    })
    @PostMapping
    public ResponseEntity<User> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "User object to be created",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = User.class),
                    examples = @ExampleObject(
                        name = "User Example",
                        value = "{\n  \"username\": \"johndoe\",\n  \"email\": \"john.doe@example.com\"\n}"
                    )
                )
            )
            @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(
        summary = "Update an existing user",
        description = "Update user information including username and email"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid user data or validation errors")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "ID of the user to update", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated user information",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = User.class),
                    examples = @ExampleObject(
                        name = "Update User Example",
                        value = "{\n  \"username\": \"johnsmith\",\n  \"email\": \"john.smith@example.com\"\n}"
                    )
                )
            )
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
    
    @Operation(
        summary = "Delete a user",
        description = "Delete a user and all associated comments (cascade delete)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true, example = "1")
            @PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Advanced Methods

    @Operation(
        summary = "Search users by name or email",
        description = "Search for users whose username or email contains the specified search term"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Invalid search term")
    })
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByNameOrEmail(
            @Parameter(description = "Search term to match against username or email",
                      required = true, example = "john")
            @RequestParam String searchTerm) {
        List<User> users = userService.searchUsersByNameOrEmail(searchTerm);
        return ResponseEntity.ok(users);
    }
    
    @Operation(
        summary = "Get users by role",
        description = "Retrieve all users assigned to a specific role"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @GetMapping("/by-role/{roleId}")
    public ResponseEntity<List<User>> getUsersByRole(
            @Parameter(description = "ID of the role to filter users by", required = true, example = "1")
            @PathVariable Long roleId) {
        List<User> users = userService.getUsersByRole(roleId);
        return ResponseEntity.ok(users);
    }

    @Operation(
        summary = "Get tasks assigned to user",
        description = "Retrieve all tasks assigned to a specific user"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = Task.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Task>> getTasksAssignedToUser(
            @Parameter(description = "ID of the user to get tasks for", required = true, example = "1")
            @PathVariable Long userId) {
        List<Task> tasks = userService.getTasksAssignedToUser(userId);
        return ResponseEntity.ok(tasks);
    }

    @Operation(
        summary = "Check if email exists",
        description = "Check if a user with the specified email already exists in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email check completed",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = Boolean.class))),
        @ApiResponse(responseCode = "400", description = "Invalid email format")
    })
    @GetMapping("/exists-by-email")
    public ResponseEntity<Boolean> existsByEmail(
            @Parameter(description = "Email address to check for existence",
                      required = true, example = "john.doe@example.com")
            @RequestParam String email) {
        Boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}
