package dev.pedrao.portfolio_api.repository;

import dev.pedrao.portfolio_api.model.Skill;
import dev.pedrao.portfolio_api.model.enums.SkillType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkillRepository extends MongoRepository<Skill, String> {

    List<Skill> findAllByType(SkillType type);
}