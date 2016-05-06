package ru.unc6.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String address;
    private String company;
    private String position;
    private String phone;

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "address", length = -1)
    @JsonIgnore
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "company", length = -1)
    @JsonIgnore
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Basic
    @Column(name = "position", length = -1)
    @JsonIgnore
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    @Basic
    @Column(name = "phone", length = -1)
    @JsonIgnore
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
