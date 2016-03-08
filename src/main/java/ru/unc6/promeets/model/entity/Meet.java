package ru.unc6.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "meets")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "meetId")
public class Meet {
    private long meetId;
    private String title;
    private Timestamp time;
    private List<MeetAim> aims;
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
    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    @Basic
    @Column(name = "time")
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
        if (title != null ? !title.equals(meet.title) : meet.title != null) return false;
        if (time != null ? !time.equals(meet.time) : meet.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (meetId ^ (meetId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
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
    @JoinColumn(name = "admin_id", referencedColumnName = "user_id")
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
    @OneToMany(mappedBy = "userMeetPK.meet")
    public List<UserMeet> getUsers() {
        return users;
    }

    public void setUsers(List<UserMeet> users) {
        this.users = users;
    }


    @JsonIgnore
    @OneToMany(mappedBy = "meet")
    public List<MeetAim> getAims() {
        return aims;
    }

    public void setAims(List<MeetAim> aims) {
        this.aims = aims;
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
