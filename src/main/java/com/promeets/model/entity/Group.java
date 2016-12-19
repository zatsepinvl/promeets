package com.promeets.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "groups")
public class Group implements Serializable {
    private long groupId;
    private String title;
    private String status;
    private long time;
    private Chat chat;
    private File image;
    private User admin;

    @Id
    @Column(name = "group_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "title", nullable = false, length = -1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    @Basic
    @Column(name = "status", length = -1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public long getTime() {
        return time;
    }

    public void setTime(long createdTime) {
        this.time = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (groupId != group.groupId) return false;
        if (time != group.time) return false;
        if (title != null ? !title.equals(group.title) : group.title != null) return false;
        if (status != null ? !status.equals(group.status) : group.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (groupId ^ (groupId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }



    @ManyToOne
    @JoinColumn(name = "image_id", referencedColumnName = "file_id")
    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "user_id", nullable = false)
    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    @OneToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id", nullable = false)
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
