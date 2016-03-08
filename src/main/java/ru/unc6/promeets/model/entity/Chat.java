package ru.unc6.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "chats")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "chatId")
public class Chat {
    private long chatId;
    private String name;
    private List<Message> messages;
    private List<User> users;

    @Id
    @Column(name = "chat_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        if (chatId != chat.chatId) return false;
        if (name != null ? !name.equals(chat.name) : chat.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (chatId ^ (chatId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "chat")
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "chats")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
