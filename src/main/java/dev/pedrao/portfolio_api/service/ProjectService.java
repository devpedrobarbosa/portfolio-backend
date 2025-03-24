package dev.pedrao.portfolio_api.service;

import dev.pedrao.portfolio_api.model.Project;
import dev.pedrao.portfolio_api.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
    * Save a new project
    *
    * @param project the project to save
    * @return the saved project
    */
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    /**
    * Find all projects
    *
    * @return list of all projects
    */
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    /**
    * Find a project by its ID
    *
    * @param id the ID to search for
    * @return the project if found, empty Optional otherwise
    */
    public Optional<Project> findById(String id) {
        return projectRepository.findById(id);
    }

    /**
    * Update an existing project
    *
    * @param id the ID of the project to update
    * @param updatedProject the updated project information
    * @return the updated project if found, null otherwise
    */
    public Project update(String id, Project updatedProject) {
        Optional<Project> existingProject = projectRepository.findById(id);
        
        if (existingProject.isPresent()) {
            updatedProject.setId(id);
            return projectRepository.save(updatedProject);
        }
        
        return null;
    }

    /**
    * Delete a project by its ID
    *
    * @param id the ID of the project to delete
    */
    public void delete(String id) {
        projectRepository.deleteById(id);
    }
}