package com.promeets.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vladimir on 27.04.2016.
 */
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {
    private long userId;
    private User user;
    private boolean showEmail;
    private String address;
    private String company;
    private String position;
    private String phone;

    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "address", length = -1)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "company", length = -1)
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "position", length = -1)
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

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Transient
    public String getEmail() {
        return showEmail ? user.getEmail() : null;
    }


}
