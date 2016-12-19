package com.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Vladimir on 18.05.2016.
 */
@Entity
@Table(name = "user_group_invites", schema = "public", catalog = "promeets_db")
public class UserGroupInvite {
    private UserGroupPK userGroupPK;
    private User inviter;
    private boolean accepted;

    public UserGroupInvite() {
        userGroupPK = new UserGroupPK();
    }

    @EmbeddedId
    @JsonIgnore
    public UserGroupPK getUserGroupPK() {
        return userGroupPK;
    }

    public void setUserGroupPK(UserGroupPK userGroupPK) {
        this.userGroupPK = userGroupPK;
    }

    @ManyToOne
    @JoinColumn(name = "inviter_id", referencedColumnName = "user_id")
    public User getInviter() {
        return inviter;
    }

    public void setInviter(User inviter) {
        this.inviter = inviter;
    }

    @Transient
    public User getUser() {
        return userGroupPK.getUser();
    }

    public void setUser(User user) {
        userGroupPK.setUser(user);
    }

    @Transient
    public Group getGroup() {
        return userGroupPK.getGroup();
    }

    public void setGroup(Group group) {
        userGroupPK.setGroup(group);
    }

    @Column(name="accepted")
    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
