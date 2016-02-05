package ru.unc6.promeets.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "user_meets", schema = "public", catalog = "promeets_db")
@IdClass(UserMeetsPK.class)
public class UserMeet implements Serializable {
    private long userId;
    private long meetId;
    private short editBoardPermission;

    @Id
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "meet_id", nullable = false)
    public long getMeetId() {
        return meetId;
    }

    public void setMeetId(long meetId) {
        this.meetId = meetId;
    }

    @Basic
    @Column(name = "edit_board_permission", nullable = false)
    public short getEditBoardPermission() {
        return editBoardPermission;
    }

    public void setEditBoardPermission(short editBoardPermission) {
        this.editBoardPermission = editBoardPermission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMeet userMeet = (UserMeet) o;

        if (userId != userMeet.userId) return false;
        if (meetId != userMeet.meetId) return false;
        if (editBoardPermission != userMeet.editBoardPermission) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (meetId ^ (meetId >>> 32));
        result = 31 * result + (int) editBoardPermission;
        return result;
    }

}
