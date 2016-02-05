package ru.unc6.promeets.model.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Vladimir on 30.01.2016.
 */
public class ChatUserPK implements Serializable {
    private long chatId;
    private long userId;

    @Column(name = "chat_id", nullable = false)
    @Id
    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Column(name = "user_id", nullable = false)
    @Id
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

        ChatUserPK that = (ChatUserPK) o;

        if (chatId != that.chatId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (chatId ^ (chatId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }
}
