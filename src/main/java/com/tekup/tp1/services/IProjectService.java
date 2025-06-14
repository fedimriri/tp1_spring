package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Project;

public interface IProjectService {
    Project createProject(Project project);
    Project updateProject(Long id, Project project);
    void deleteProject(Long id);
    
    Optional<Project> getProjectById(Long id);
    List<Project> getAllProjects();
}