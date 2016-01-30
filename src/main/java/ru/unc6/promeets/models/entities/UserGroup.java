package ru.unc6.promeets.models.entities;

import javax.persistence.*;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "user_groups", schema = "public", catalog = "promeets_db")
@IdClass(UserGroupsPK.class)
public class UserGroup {
    private long userId;
    private long groupId;
    private short createMeetPermission;
    private short invitePermission;

    @Id
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "group_id", nullable = false)
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "create_meet_permission", nullable = false)
    public short getCreateMeetPermission() {
        return createMeetPermission;
    }

    public void setCreateMeetPermission(short createMeetPermission) {
        this.createMeetPermission = createMeetPermission;
    }

    @Basic
    @Column(name = "invite_permission", nullable = false)
    public short getInvitePermission() {
        return invitePermission;
    }

    public void setInvitePermission(short invitePermission) {
        this.invitePermission = invitePermission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGroup that = (UserGroup) o;

        if (userId != that.userId) return false;
        if (groupId != that.groupId) return false;
        if (createMeetPermission != that.createMeetPermission) return false;
        if (invitePermission != that.invitePermission) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (groupId ^ (groupId >>> 32));
        result = 31 * result + (int) createMeetPermission;
        result = 31 * result + (int) invitePermission;
        return result;
    }
}
