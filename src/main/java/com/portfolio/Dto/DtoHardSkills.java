package com.portfolio.Dto;

import javax.validation.constraints.NotBlank;

public class DtoHardSkills {

    @NotBlank
    private String name;
    @NotBlank
    private int percentage;
    @NotBlank
    private String img;
    @NotBlank
    private String type_img;

    //Constructors
    public DtoHardSkills() {
    }

    public DtoHardSkills(String name, int percentage, String img, String type_img) {
        this.name = name;
        this.percentage = percentage;
        this.img = img;
        this.type_img = type_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
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

}
