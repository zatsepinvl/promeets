package com.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by Vladimir on 04.05.2016.
 */
@Entity
@Table(name = "user_files", schema = "public", catalog = "promeets_db")
public class UserFile {
    private UserFilePK userFilePK;

    public UserFile() {
        userFilePK = new UserFilePK();
    }

    @EmbeddedId
    @JsonIgnore
    public UserFilePK getUserFilePK() {
        return userFilePK;
    }

    public void setUserFilePK(UserFilePK userFilePK) {
        this.userFilePK = userFilePK;
    }

    @Transient
    @JsonIgnore
    public User getUser() {
        return userFilePK.getUser();
    }

    @JsonProperty
    public void setUser(User user) {
        userFilePK.setUser(user);
    }

    @Transient
    public File getFile() {
        return userFilePK.getFile();
    }

    public void setFile(File file) {
        userFilePK.setFile(file);
    }
}
