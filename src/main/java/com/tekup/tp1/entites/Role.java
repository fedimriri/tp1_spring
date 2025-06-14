package com.tekup.tp1.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Role name cannot be empty")
    @Size(min = 3, max = 20, message = "Role name must be between 3 and 20 characters")
    private String name;
    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}