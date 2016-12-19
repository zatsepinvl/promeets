package com.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "user_chats", schema = "public", catalog = "promeets_db")
public class UserChat implements Serializable {
    private UserChatPK userChatPK;
    private int newMessagesCount;
    private UserMessage lastUserMessage;


    public UserChat() {
        userChatPK = new UserChatPK();
    }

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

    @Transient
    public int getNewMessagesCount() {
        return newMessagesCount;
    }

    public void setNewMessagesCount(int messagesCount) {
        this.newMessagesCount = messagesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChat userChat = (UserChat) o;
        if (newMessagesCount != userChat.newMessagesCount) return false;
        return userChatPK != null ? userChatPK.equals(userChat.userChatPK) : userChat.userChatPK == null;

    }

    @Override
    public int hashCode() {
        int result = userChatPK != null ? userChatPK.hashCode() : 0;
        result = 31 * result + newMessagesCount;
        result = 31 * result + (lastUserMessage != null ? lastUserMessage.hashCode() : 0);
        return result;
    }

    @Transient
    public UserMessage getLastUserMessage() {
        return lastUserMessage;
    }

    public void setLastUserMessage(UserMessage lastMessage) {
        this.lastUserMessage = lastMessage;
    }
}
