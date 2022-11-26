package com.portfolio.Dto;

import javax.validation.constraints.NotNull;

public class DtoUser {

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private int age;
    @NotNull
    private String img;
    @NotNull
    private String type_img;

    @NotNull
    private String username;

    @NotNull
    private String description;
    @NotNull
    private String linkedin;
    @NotNull
    private String github;

    public DtoUser() {
    }

    public DtoUser(String name, String surname, int age, String img, String type_img, String description, String linkedin, String github) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.img = img;
        this.type_img = type_img;
        this.description = description;
        this.linkedin = linkedin;
        this.github = github;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType_img() {
        return type_img;
    }

    public void setType_img(String type_img) {
        this.type_img = type_img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

}
