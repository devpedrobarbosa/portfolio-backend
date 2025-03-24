package dev.pedrao.portfolio_api.model;

import dev.pedrao.portfolio_api.model.enums.SkillType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Skill {

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