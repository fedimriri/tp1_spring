package com.tekup.tp1.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tekup.tp1.entites.Project;
import com.tekup.tp1.exception.ProjectNotFoundException;
import com.tekup.tp1.services.IProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
@Tag(name = "Project Management", description = "APIs for managing projects including CRUD operations and advanced filtering")
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
    
    @Operation(summary = "Create a new project", description = "Create a new project with validation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Project created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))),
        @ApiResponse(responseCode = "400", description = "Invalid project data")
    })
    @PostMapping
    public ResponseEntity<Project> createProject(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Project object to be created",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Project.class),
                    examples = @ExampleObject(value = "{\n  \"name\": \"New Project\",\n  \"description\": \"Project description\",\n  \"startDate\": \"2024-01-01\",\n  \"endDate\": \"2024-12-31\",\n  \"status\": \"ACTIVE\"\n}")
                )
            )
            @RequestBody Project project) {
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
    
    @Operation(summary = "Search projects by keyword", description = "Search projects by name or description containing keyword")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjectsByKeyword(
            @Parameter(description = "Keyword to search in project name or description", example = "web")
            @RequestParam String keyword) {
        List<Project> projects = projectService.searchProjectsByKeyword(keyword);
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Project>> getProjectsByUserParticipation(@PathVariable Long userId) {
        List<Project> projects = projectService.getProjectsByUserParticipation(userId);
        return ResponseEntity.ok(projects);
    }
}
