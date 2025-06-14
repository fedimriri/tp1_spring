package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tekup.tp1.entites.Role;
import com.tekup.tp1.entites.User;
import com.tekup.tp1.exception.RoleNotFoundException;
import com.tekup.tp1.exception.UserNotFoundException;
import com.tekup.tp1.repositories.RoleRepository;
import com.tekup.tp1.repositories.UserRepository;

@Service
public class RoleServiceImpl implements IRoleService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
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

    @Override
    public List<User> getUsersWithRole(Long roleId) {
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (roleOpt.isPresent()) {
            return userRepository.findByRole(roleOpt.get());
        }
        return List.of(); 
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) throws UserNotFoundException, RoleNotFoundException {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Role> roleOpt = roleRepository.findById(roleId);

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found with Id: " + userId);
        }
        if (roleOpt.isEmpty()) {
            throw new RoleNotFoundException("Role not found with Id: " + roleId);
        }

        User user = userOpt.get();
        Role role = roleOpt.get();

        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public User unassignRoleFromUser(Long userId, Long roleId) throws UserNotFoundException, RoleNotFoundException {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Role> roleOpt = roleRepository.findById(roleId);

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found with Id: " + userId);
        }
        if (roleOpt.isEmpty()) {
            throw new RoleNotFoundException("Role not found with Id: " + roleId);
        }

        User user = userOpt.get();
        Role role = roleOpt.get();

        user.getRoles().remove(role);
        return userRepository.save(user);
    }
}