package com.promeets.model.entity;

import javax.persistence.*;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "user_chats", schema = "public", catalog = "promeets_db")
public class ChatUser {
    private ChatUserPK chatUserPK;

    @EmbeddedId
    public ChatUserPK getChatUserPK() {
        return chatUserPK;
    }

    public void setChatUserPK(ChatUserPK charUserPK) {
        this.chatUserPK = charUserPK;
    }

    @Transient
    public Chat getChat() {
        return chatUserPK.getChat();
    }

    public void setChat(Chat chat) {
        chatUserPK.setChat(chat);
    }

    @Transient
    public User getUser() {
        return chatUserPK.getUser();
    }

    public void setUser(User user) {
        chatUserPK.setUser(user);
    }

}
