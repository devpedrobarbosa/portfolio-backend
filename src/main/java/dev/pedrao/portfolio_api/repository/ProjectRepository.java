package dev.pedrao.portfolio_api.repository;

import dev.pedrao.portfolio_api.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    // Custom query methods can be added here
}