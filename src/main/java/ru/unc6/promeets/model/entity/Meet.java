package ru.unc6.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "meets")
public class Meet implements Serializable {
    private long meetId;
    private String title;
    private long time;
    private User admin;
    private String location;
    private String description;
    private MeetType type;
    private Group group;

    @Id
    @Column(name = "meet_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getMeetId() {
        return meetId;
    }

    public void setMeetId(long meetId) {
        this.meetId = meetId;
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
    @Column(name = "time")
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meet meet = (Meet) o;

        if (meetId != meet.meetId) return false;
        if (title != null ? !title.equals(meet.title) : meet.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (meetId ^ (meetId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }


    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "user_id", nullable = false)
    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }


    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    public MeetType getType() {
        return type;
    }

    public void setType(MeetType type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    public Group getGroup() {
        return group;
    }


    public void setGroup(Group group) {
        this.group = group;
    }

}
