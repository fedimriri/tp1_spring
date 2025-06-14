package com.tekup.tp1.services;

import java.util.List;
import java.util.Optional;

import com.tekup.tp1.entites.Project;
import com.tekup.tp1.exception.ProjectNotFoundException;

public interface IProjectService {
    Project createProject(Project project);
    Project updateProject(Long id, Project project) throws ProjectNotFoundException;
    void deleteProject(Long id) throws ProjectNotFoundException;
    
    Optional<Project> getProjectById(Long id);
    List<Project> getAllProjects();
}
