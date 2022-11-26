package com.portfolio.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "surname")
    private String surname;
    @NotNull
    @Column(name = "age")
    private int age;

    @Lob
    @NotNull
    @Column(name = "img")
    private String img;

    @NotNull
    @Column(name = "type_img")
    private String type_img;

    @Lob
    @NotNull
    @Column(name = "description")
    private String description;
    @NotNull
    @Column(name = "linkedin")
    private String linkedin;
    @NotNull
    @Column(name = "github")
    private String github;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<WorkExperience> workExperience = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Education> education = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<HardSkill> hardSkill = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<SoftSkill> softSkill = new HashSet<>();

    //Constructors
    public User() {
    }

    public User(String name, String surname, int age, String username, String img, String type_img, String description, String linkedin, String github) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.username = username;
        this.img = img;
        this.type_img = type_img;
        this.description = description;
        this.linkedin = linkedin;
        this.github = github;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<WorkExperience> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Set<WorkExperience> workExperience) {
        this.workExperience = workExperience;
    }

    public Set<Education> getEducation() {
        return education;
    }

    public void setEducation(Set<Education> education) {
        this.education = education;
    }

    public Set<HardSkill> getHardSkill() {
        return hardSkill;
    }

    public void setHardSkill(Set<HardSkill> hardSkill) {
        this.hardSkill = hardSkill;
    }

    public Set<SoftSkill> getSoftSkill() {
        return softSkill;
    }

    //Getters & Setters
    public void setSoftSkill(Set<SoftSkill> softSkill) {
        this.softSkill = softSkill;
    }

    //Methods
    public void addEducation(Education ed) {
        this.education.add(ed);
        ed.setUser(this);
    }

    public void removeEducation(int ed_id) {
        Education ed = this.education.stream().filter(t -> t.getId() == ed_id).findFirst().orElse(null);
        if (ed != null) {
            this.education.remove(ed);
        }
    }

    public void addWork(WorkExperience we) {
        this.workExperience.add(we);
        we.setUser(this);
    }

    public void removeWorkExp(int we_id) {
        WorkExperience we = this.workExperience.stream().filter(t -> t.getId() == we_id).findFirst().orElse(null);
        if (we != null) {
            this.workExperience.remove(we);
        }
    }

    public void addHardSkill(HardSkill hs) {
        this.hardSkill.add(hs);
        hs.getUsers().add(this);
    }

    public void removeHardSkill(int hsId) {
        HardSkill hs = this.hardSkill.stream().filter(t -> t.getId() == hsId).findFirst().orElse(null);
        if (hs != null) {
            this.hardSkill.remove(hs);
            hs.getUsers().remove(this);
        }
    }

    public void addSoftSkill(SoftSkill ss) {
        this.softSkill.add(ss);
        ss.getUsers().add(this);
    }

    public void removeSoftSkill(int ssId) {
        SoftSkill ss = this.softSkill.stream().filter(t -> t.getId() == ssId).findFirst().orElse(null);
        if (ss != null) {
            this.softSkill.remove(ss);
            ss.getUsers().remove(this);
        }
    }

}
