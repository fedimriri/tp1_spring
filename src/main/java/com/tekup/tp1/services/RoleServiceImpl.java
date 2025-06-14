package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tekup.tp1.entites.Role;
import com.tekup.tp1.exception.RoleNotFoundException;
import com.tekup.tp1.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements IRoleService {
    private RoleRepository roleRepository;
    
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    
    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }
    
    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
    
    @Override
    public Role updateRole(Long id, Role updatedRole) throws RoleNotFoundException {
        Optional<Role> existingRoleOpt = roleRepository.findById(id);
        if (existingRoleOpt.isPresent()) {
            Role existingRole = existingRoleOpt.get();
            existingRole.setName(updatedRole.getName());
            return roleRepository.save(existingRole);
        } else {
            throw new RoleNotFoundException("Role not found with Id: " + id);
        }
    }
    
    @Override
    public void deleteRole(Long id) throws RoleNotFoundException{
        if (!roleRepository.existsById(id)) {
            throw new RoleNotFoundException("Role not found with Id: " + id);
        }
        roleRepository.deleteById(id);
    }
    
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}