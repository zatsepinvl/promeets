package ru.unc6.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "user_chats", schema = "public", catalog = "promeets_db")
public class UserChat {
    private UserChatPK userChatPK;

    @EmbeddedId
    @JsonIgnore
    public UserChatPK getUserChatPK() {
        return userChatPK;
    }

    public void setUserChatPK(UserChatPK charUserPK) {
        this.userChatPK = charUserPK;
    }

    @Transient
    public Chat getChat() {
        return userChatPK.getChat();
    }

    public void setChat(Chat chat) {
        userChatPK.setChat(chat);
    }

    @Transient
    public User getUser() {
        return userChatPK.getUser();
    }

    public void setUser(User user) {
        userChatPK.setUser(user);
    }

}
