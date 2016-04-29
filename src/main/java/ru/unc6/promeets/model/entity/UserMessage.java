package ru.unc6.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vladimir on 12.04.2016.
 */
@Entity
@Table(name = "user_messages", schema = "public", catalog = "promeets_db")
public class UserMessage implements Serializable {

    private UserMessagePK userMessagePK;
    private boolean viewed;

    public UserMessage() {
        userMessagePK = new UserMessagePK();
    }

    @EmbeddedId
    @JsonIgnore
    public UserMessagePK getUserMessagePK() {
        return userMessagePK;
    }

    public void setUserMessagePK(UserMessagePK userMessagePK) {
        this.userMessagePK = userMessagePK;
    }

    @Basic
    @Column(name = "viewed")
    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    @Transient
    public User getUser() {
        return userMessagePK.getUser();
    }

    public void setUser(User user) {
        userMessagePK.setUser(user);
    }

    @Transient
    public Message getMessage() {
        return userMessagePK.getMessage();
    }

    public void setMessage(Message message) {
        userMessagePK.setMessage(message);
    }

    @Transient
    public boolean isSender() {
        return getUser().getUserId() == getMessage().getUser().getUserId();
    }
}
