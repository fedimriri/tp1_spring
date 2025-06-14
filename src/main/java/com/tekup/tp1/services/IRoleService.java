package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Role;
import com.tekup.tp1.exception.RoleNotFoundException;

public interface IRoleService {
    Role createRole(Role role);
    Role updateRole(Long id, Role role) throws RoleNotFoundException;
    void deleteRole(Long id) throws RoleNotFoundException;
    
    Optional<Role> getRoleById(Long id);
    List<Role> getAllRoles();
    Role findByName(String name);
}
