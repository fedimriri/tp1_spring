package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Role;

public interface IRoleService {
    Role createRole(Role role);
    Role updateRole(Long id, Role role);
    void deleteRole(Long id);
    
    Optional<Role> getRoleById(Long id);
    List<Role> getAllRoles();
    Role findByName(String name);
}