/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Alex
 */

@Entity
@Table(name = "user_meet_info", schema = "public", catalog = "promeets_db")
public class UserMeetInfo implements Serializable {
    
    private UserMeetPK userMeetPK;
    private boolean online;
    private boolean connected;

    public UserMeetInfo() {
        userMeetPK = new UserMeetPK();
    }

    @EmbeddedId
    @JsonIgnore
    public UserMeetPK getUserMeetPK() {
        return userMeetPK;
    }

    public void setUserMeetPK(UserMeetPK userMeetPK) {
        this.userMeetPK = userMeetPK;
    }

    @Transient
    public User getUser() {
        return userMeetPK.getUser();
    }

    public void setUser(User user) {
        userMeetPK.setUser(user);
    }

    @Transient
    public Meet getMeet() {
        return userMeetPK.getMeet();
    }

    public void setMeet(Meet meet) {
        userMeetPK.setMeet(meet);
    }

    @Basic
    @Column(name = "online")
    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Basic
    @Column(name = "connected")
    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
