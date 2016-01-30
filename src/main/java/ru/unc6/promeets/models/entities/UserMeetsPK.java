package ru.unc6.promeets.models.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Vladimir on 30.01.2016.
 */
public class UserMeetsPK implements Serializable {
    private long userId;
    private long meetId;

    @Column(name = "user_id", nullable = false)
    @Id
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "meet_id", nullable = false)
    @Id
    public long getMeetId() {
        return meetId;
    }

    public void setMeetId(long meetId) {
        this.meetId = meetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMeetsPK that = (UserMeetsPK) o;

        if (userId != that.userId) return false;
        if (meetId != that.meetId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (meetId ^ (meetId >>> 32));
        return result;
    }
}
