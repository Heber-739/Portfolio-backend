package com.portfolio.Dto;

import javax.validation.constraints.NotBlank;

public class DtoMail {

    @NotBlank
    private String name;
    @NotBlank
    private String mail;
    @NotBlank
    private String message;

    public DtoMail() {
    }

    public DtoMail(String name, String mail, String message) {
        this.name = name;
        this.mail = mail;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
