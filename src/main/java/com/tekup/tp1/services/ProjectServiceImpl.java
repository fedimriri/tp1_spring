package com.tekup.tp1.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tekup.tp1.entites.Project;
import com.tekup.tp1.exception.ProjectNotFoundException;
import com.tekup.tp1.repositories.ProjectRepository;

@Service
public class ProjectServiceImpl implements IProjectService {
    private ProjectRepository projectRepository;
    
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    @Override
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
    
    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }
    
    @Override
    public Project updateProject(Long id, Project updatedProject) throws ProjectNotFoundException{
        Optional<Project> existingProjectOpt = projectRepository.findById(id);
        if (existingProjectOpt.isPresent()) {
            Project existingProject = existingProjectOpt.get();
            existingProject.setName(updatedProject.getName());
            existingProject.setDescription(updatedProject.getDescription());
            existingProject.setStartDate(updatedProject.getStartDate());
            existingProject.setEndDate(updatedProject.getEndDate());
            existingProject.setStatus(updatedProject.getStatus());
            return projectRepository.save(existingProject);
        } else {
            throw new ProjectNotFoundException("Project not found with Id: " + id);
        }
    }
    
    @Override
    public void deleteProject(Long id) throws ProjectNotFoundException{
        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException("Project not found with Id: " + id);
        }
        projectRepository.deleteById(id);
    }

    // Advanced methods implementation
    @Override
    public List<Project> getActiveProjects(String status) {
        return projectRepository.findByStatus(status);
    }

    @Override
    public List<Project> getUpcomingProjects() {
        return projectRepository.findUpcomingProjects(LocalDate.now());
    }

    @Override
    public List<Project> searchProjectsByKeyword(String keyword) {
        return projectRepository.findByNameContainingOrDescriptionContaining(keyword);
    }

    @Override
    public List<Project> getProjectsByUserParticipation(Long userId) {
        return projectRepository.findProjectsByUserParticipation(userId);
    }
}