package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Project;
import com.tekup.tp1.exception.ProjectNotFoundException;

public interface IProjectService {
    // CRUD operations
    Project createProject(Project project);
    Project updateProject(Long id, Project project) throws ProjectNotFoundException;
    void deleteProject(Long id) throws ProjectNotFoundException;

    Optional<Project> getProjectById(Long id);
    List<Project> getAllProjects();

    // Advanced methods
    List<Project> getActiveProjects(String status);
    List<Project> getUpcomingProjects();
    List<Project> searchProjectsByKeyword(String keyword);
    List<Project> getProjectsByUserParticipation(Long userId);
}
