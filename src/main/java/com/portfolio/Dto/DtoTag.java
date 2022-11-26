package com.portfolio.Dto;

import javax.validation.constraints.NotBlank;

public class DtoTag {

    @NotBlank
    private String abbreviation;
    @NotBlank
    private String name;
    @NotBlank
    private int educationId;

    // Contructors
    public DtoTag() {
    }

    public DtoTag(String abbreviation, String name, int educationId) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.educationId = educationId;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEducationId() {
        return educationId;
    }

    public void setEducationId(int educationId) {
        this.educationId = educationId;
    }

}
