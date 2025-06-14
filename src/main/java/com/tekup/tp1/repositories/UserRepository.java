package com.tekup.tp1.repositories;

import com.tekup.tp1.entites.User;
import com.tekup.tp1.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Boolean existsByEmail(String email);

    // Advanced methods for User
    @Query("SELECT u FROM User u WHERE u.username LIKE %:searchTerm% OR u.email LIKE %:searchTerm%")
    List<User> findByUsernameContainingOrEmailContaining(@Param("searchTerm") String searchTerm);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r = :role")
    List<User> findByRole(@Param("role") Role role);

    @Query("SELECT u FROM User u JOIN u.assignedtasks t WHERE t.id = :taskId")
    List<User> findUsersAssignedToTask(@Param("taskId") Long taskId);
}
