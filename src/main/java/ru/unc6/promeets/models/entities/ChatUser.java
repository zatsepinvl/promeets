package ru.unc6.promeets.models.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "user_chats", schema = "public", catalog = "promeets_db")
@IdClass(ChatUserPK.class)
public class ChatUser {
    private long chatId;
    private long userId;

    @Id
    @Column(name = "chat_id", nullable = false)
    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Id
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatUser chatUser = (ChatUser) o;

        if (chatId != chatUser.chatId) return false;
        if (userId != chatUser.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (chatId ^ (chatId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }


}
