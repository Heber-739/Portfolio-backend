package com.portfolio.Dto;

import javax.validation.constraints.NotBlank;

public class DtoSoftSkill {

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    //Constructors
    public DtoSoftSkill() {
    }

    public DtoSoftSkill(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
