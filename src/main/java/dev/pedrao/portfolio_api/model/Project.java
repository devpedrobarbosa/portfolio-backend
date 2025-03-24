package dev.pedrao.portfolio_api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project {

    private String id;
    private String name;
    private String enDescription, ptDescription;
    private String[] tags;

    public Project(String name, String enDescription, String ptDescription, String[] tags) {
        this.name = name;
        this.enDescription = enDescription;
        this.ptDescription = ptDescription;
        this.tags = tags;
    }
}