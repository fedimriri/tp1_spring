package com.tekup.tp1.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tekup.tp1.entites.Role;
import com.tekup.tp1.entites.User;
import com.tekup.tp1.exception.RoleNotFoundException;
import com.tekup.tp1.exception.UserNotFoundException;
import com.tekup.tp1.services.IRoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
@Tag(name = "Role Management", description = "APIs for managing roles including CRUD operations and user role assignments")
public class RoleController {
    
    private final IRoleService roleService;
    
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }
    
    // CRUD Operations

    @Operation(summary = "Get all roles", description = "Retrieve a list of all roles in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all roles",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class)))
    })
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.getRoleById(id);
        return role.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Create a new role", description = "Create a new role with validation (name 3-20 characters)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Role created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))),
        @ApiResponse(responseCode = "400", description = "Invalid role data or validation errors")
    })
    @PostMapping
    public ResponseEntity<Role> createRole(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Role object to be created",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Role.class),
                    examples = @ExampleObject(value = "{\n  \"name\": \"ADMIN\"\n}")
                )
            )
            @RequestBody Role role) {
        try {
            Role createdRole = roleService.createRole(role);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
        try {
            Role updatedRole = roleService.updateRole(id, role);
            return ResponseEntity.ok(updatedRole);
        } catch (RoleNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        try {
            roleService.deleteRole(id);
            return ResponseEntity.noContent().build();
        } catch (RoleNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Advanced Methods
    
    @GetMapping("/by-name")
    public ResponseEntity<Role> findByName(@RequestParam String name) {
        Role role = roleService.findByName(name);
        return role != null ? ResponseEntity.ok(role) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{roleId}/users")
    public ResponseEntity<List<User>> getUsersWithRole(@PathVariable Long roleId) {
        List<User> users = roleService.getUsersWithRole(roleId);
        return ResponseEntity.ok(users);
    }
    
    @Operation(summary = "Assign role to user", description = "Assign a specific role to a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role assigned successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User or role not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/{roleId}/assign-to-user/{userId}")
    public ResponseEntity<User> assignRoleToUser(
            @Parameter(description = "ID of the role to assign", example = "1") @PathVariable Long roleId,
            @Parameter(description = "ID of the user to assign role to", example = "1") @PathVariable Long userId) {
        try {
            User user = roleService.assignRoleToUser(userId, roleId);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException | RoleNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{roleId}/unassign-from-user/{userId}")
    public ResponseEntity<User> unassignRoleFromUser(@PathVariable Long roleId, @PathVariable Long userId) {
        try {
            User user = roleService.unassignRoleFromUser(userId, roleId);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException | RoleNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
