package ru.unc6.promeets.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "meets")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="meetId")
public class Meet {
    private long meetId;
    private String name;
    private Timestamp time;
    private List<MeetNote> notes;
    private User admin;
    private Board board;

    @Id
    @Column(name = "meet_id", nullable = false)
    public long getMeetId() {
        return meetId;
    }

    public void setMeetId(long meetId) {
        this.meetId = meetId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
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

    public void setAdmin(User amdin) {
        this.admin = amdin;
    }

    @OneToOne
    @JoinColumn(name = "board_id", referencedColumnName = "board_id", nullable = false)
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
