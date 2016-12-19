package com.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vladimir on 04.05.2016.
 */
@Embeddable
public class UserFilePK implements Serializable {
    private User user;
    private File file;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "file_id")
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
