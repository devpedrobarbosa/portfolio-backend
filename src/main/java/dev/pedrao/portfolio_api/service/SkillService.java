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

    public Skill create(Skill project) {
        return skillRepository.save(project);
    }

    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    public Optional<Skill> findById(String id) {
        return skillRepository.findById(id);
    }

    public List<Skill> findAllByType(SkillType type) {
        return skillRepository.findAllByType(type);
    }

    public Skill update(String id, Skill updatedSkill) {
        Optional<Skill> existingSkill = skillRepository.findById(id);
        if(existingSkill.isPresent()) {
            updatedSkill.setId(id);
            return skillRepository.save(updatedSkill);
        }
        return null;
    }
    public void delete(String id) {
        skillRepository.deleteById(id);
    }
}
