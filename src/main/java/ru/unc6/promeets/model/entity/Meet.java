package ru.unc6.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "meets")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "meetId")
public class Meet {
    private long meetId;
    private String name;
    private Timestamp time;
    private List<MeetAim> targets;
    private List<MeetNote> notes;
    private User admin;
    private Board board;
    private List<UserMeet> users;
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meet meet = (Meet) o;

        if (meetId != meet.meetId) return false;
        if (name != null ? !name.equals(meet.name) : meet.name != null) return false;
        if (time != null ? !time.equals(meet.time) : meet.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (meetId ^ (meetId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "meet")
    public List<MeetNote> getNotes() {
        return notes;
    }

    public void setNotes(List<MeetNote> notes) {
        this.notes = notes;
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
    @JoinColumn(name = "board_id", referencedColumnName = "board_id")
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "userMeetPK.meet", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<UserMeet> getUsers() {
        return users;
    }

    public void setUsers(List<UserMeet> users) {
        this.users = users;
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

    @JsonIgnore
    @OneToMany(mappedBy = "meet")
    public List<MeetAim> getTargets() {
        return targets;
    }

    public void setTargets(List<MeetAim> targets) {
        this.targets = targets;
    }
}
