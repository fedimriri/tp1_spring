package com.tekup.tp1.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tekup.tp1.entites.Project;
import com.tekup.tp1.exception.ProjectNotFoundException;
import com.tekup.tp1.services.IProjectService;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    
    private final IProjectService projectService;
    
    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }
    
    // CRUD Operations
    
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        try {
            Project createdProject = projectService.createProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateProject(id, project);
            return ResponseEntity.ok(updatedProject);
        } catch (ProjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (ProjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Advanced Methods
    
    @GetMapping("/active")
    public ResponseEntity<List<Project>> getActiveProjects(@RequestParam String status) {
        List<Project> projects = projectService.getActiveProjects(status);
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<Project>> getUpcomingProjects() {
        List<Project> projects = projectService.getUpcomingProjects();
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjectsByKeyword(@RequestParam String keyword) {
        List<Project> projects = projectService.searchProjectsByKeyword(keyword);
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Project>> getProjectsByUserParticipation(@PathVariable Long userId) {
        List<Project> projects = projectService.getProjectsByUserParticipation(userId);
        return ResponseEntity.ok(projects);
    }
}
