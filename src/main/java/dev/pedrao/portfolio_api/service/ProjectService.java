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

    public Project create(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Optional<Project> findById(String id) {
        return projectRepository.findById(id);
    }

    public Project update(String id, Project updatedProject) {
        Optional<Project> existingProject = projectRepository.findById(id);
        if(existingProject.isPresent()) {
            updatedProject.setId(id);
            return projectRepository.save(updatedProject);
        }
        return null;
    }

    public void delete(String id) {
        projectRepository.deleteById(id);
    }
}