package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Role;
import com.tekup.tp1.entites.User;
import com.tekup.tp1.exception.RoleNotFoundException;
import com.tekup.tp1.exception.UserNotFoundException;

public interface IRoleService {
    // CRUD operations
    Role createRole(Role role);
    Role updateRole(Long id, Role role) throws RoleNotFoundException;
    void deleteRole(Long id) throws RoleNotFoundException;

    Optional<Role> getRoleById(Long id);
    List<Role> getAllRoles();
    Role findByName(String name);

    // Advanced methods
    List<User> getUsersWithRole(Long roleId);
    User assignRoleToUser(Long userId, Long roleId) throws UserNotFoundException, RoleNotFoundException;
    User unassignRoleFromUser(Long userId, Long roleId) throws UserNotFoundException, RoleNotFoundException;
}
