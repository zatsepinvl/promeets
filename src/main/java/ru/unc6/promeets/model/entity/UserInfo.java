package ru.unc6.promeets.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vladimir on 27.04.2016.
 */
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {
    private User user;
    private boolean showEmail;

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "show_email")
    public boolean isShowEmail() {
        return showEmail;
    }

    public void setShowEmail(boolean showEmail) {
        this.showEmail = showEmail;
    }

    @Transient
    public String getEmial() {
        return showEmail ? user.getEmail() : null;
    }
}
