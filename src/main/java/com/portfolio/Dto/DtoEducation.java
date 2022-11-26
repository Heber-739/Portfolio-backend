package com.portfolio.Dto;

import javax.validation.constraints.NotBlank;

public class DtoEducation {

    @NotBlank
    private String name;
    @NotBlank
    private String link;
    @NotBlank
    private boolean finish;
    @NotBlank
    private String img;
    @NotBlank
    private String type_img;

    // Constructors
    public DtoEducation() {
    }

    public DtoEducation(String name, String link, boolean finish, String img, String type_img) {
        this.name = name;
        this.link = link;
        this.finish = finish;
        this.img = img;
        this.type_img = type_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
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
