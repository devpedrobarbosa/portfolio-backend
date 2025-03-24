package dev.pedrao.portfolio_api.service;

import dev.pedrao.portfolio_api.model.Skill;
import dev.pedrao.portfolio_api.model.enums.SkillType;
import dev.pedrao.portfolio_api.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    /**
     * Save a new project
     *
     * @param project the project to save
     * @return the saved project
     */
    public Skill create(Skill project) {
        return skillRepository.save(project);
    }

    /**
     * Find all projects
     *
     * @return list of all projects
     */
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    /**
     * Find a project by its ID
     *
     * @param id the ID to search for
     * @return the project if found, empty Optional otherwise
     */
    public Optional<Skill> findById(String id) {
        return skillRepository.findById(id);
    }

    /**
     * Find all projects
     *
     * @return list of all projects
     */
    public List<Skill> findAllByType(SkillType type) {
        return skillRepository.findAllByType(type);
    }

    /**
     * Update an existing project
     *
     * @param id the ID of the project to update
     * @param updatedSkill the updated project information
     * @return the updated project if found, null otherwise
     */
    public Skill update(String id, Skill updatedSkill) {
        Optional<Skill> existingSkill = skillRepository.findById(id);

        if(existingSkill.isPresent()) {
            updatedSkill.setId(id);
            return skillRepository.save(updatedSkill);
        }

        return null;
    }

    /**
     * Delete a project by its ID
     *
     * @param id the ID of the project to delete
     */
    public void delete(String id) {
        skillRepository.deleteById(id);
    }
}
