package com.portfolio.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "education")
public class Education implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    @Column(name = "finish")
    private boolean finish;

    @Lob
    @NotNull
    @Column(name = "img")
    private String img;

    @NotNull
    @Column(name = "type_img")
    private String type_img;

    @ManyToMany(mappedBy = "educations", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Tag> tags = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user;

    // Constructors
    public Education() {
    }

    public Education(String name, String link, boolean finish, String img, String type_img) {
        this.name = name;
        this.link = link;
        this.finish = finish;
        this.img = img;
        this.type_img = type_img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getEducations().add(this);
    }

    public void removeTag(int tagid) {
        Tag tag = this.tags.stream().filter(t -> t.getId() == tagid).findFirst().orElse(null);
        if (tag != null) {
            this.tags.remove(tag);
            tag.getEducations().remove(this);
        }
    }

}
