package ru.unc6.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "user_meets", schema = "public", catalog = "promeets_db")
public class UserMeet implements Serializable {
    private UserMeetPK userMeetPK;
    private short editBoardPermission;
    private boolean viewed;

    public UserMeet() {
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

    @Basic
    @Column(name = "edit_board_permission", nullable = false)
    public short getEditBoardPermission() {
        return editBoardPermission;
    }

    public void setEditBoardPermission(short editBoardPermission) {
        this.editBoardPermission = editBoardPermission;
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
    @Column(name = "viewed")
    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}
