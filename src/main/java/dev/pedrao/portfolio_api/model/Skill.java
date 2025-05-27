package dev.pedrao.portfolio_api.model;

import dev.pedrao.portfolio_api.model.enums.SkillType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Skill {

    @Id
    private String id;
    private String name;
    private String website;
    private String icon;
    private SkillType type;

    public Skill(String name, String website, String icon, SkillType type) {
        this.name = name;
        this.website = website;
        this.icon = icon;
        this.type = type;
    }
}