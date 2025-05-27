package dev.pedrao.portfolio_api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Project {

    @Id
    private String id;
    private String name;
    private String enDescription, ptDescription;
    private String githubLink;
    private String[] tags;

    public Project(String name, String enDescription, String ptDescription, String githubLink, String[] tags) {
        this.name = name;
        this.enDescription = enDescription;
        this.ptDescription = ptDescription;
        this.githubLink = githubLink;
        this.tags = tags;
    }
}