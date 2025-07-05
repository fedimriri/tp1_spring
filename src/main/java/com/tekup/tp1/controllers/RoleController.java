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

@RestController
@RequestMapping("/api/v0/roles")
@CrossOrigin(origins = "*")
public class RoleController {
    
    private final IRoleService roleService;
    
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }
    
    // CRUD Operations

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
    
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
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

    @PostMapping("/{roleId}/assign-to-user/{userId}")
    public ResponseEntity<User> assignRoleToUser(
            @PathVariable Long roleId,
            @PathVariable Long userId) {
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
