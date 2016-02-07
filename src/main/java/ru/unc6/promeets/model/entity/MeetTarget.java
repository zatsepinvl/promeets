package ru.unc6.promeets.model.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "meet_targets", schema = "public", catalog = "promeets_db")
public class MeetTarget {
    private long targetId;
    private String text;
    private List<BoardItem> boardItems;
    private List<MeetNote> notes;
    private Meet meet;

    @Id
    @Column(name = "target_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    @Basic
    @Column(name = "text", nullable = false, length = -1)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeetTarget that = (MeetTarget) o;

        if (targetId != that.targetId) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (targetId ^ (targetId >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "target")
    public List<BoardItem> getBoardItems() {
        return boardItems;
    }

    public void setBoardItems(List<BoardItem> boardItems) {
        this.boardItems = boardItems;
    }

    @OneToMany(mappedBy = "target")
    public List<MeetNote> getNotes() {
        return notes;
    }

    public void setNotes(List<MeetNote> notes) {
        this.notes = notes;
    }

    @ManyToOne
    @JoinColumn(name = "meet_id", referencedColumnName = "meet_id", nullable = false)
    public Meet getMeet() {
        return meet;
    }

    public void setMeet(Meet meet) {
        this.meet = meet;
    }
}
