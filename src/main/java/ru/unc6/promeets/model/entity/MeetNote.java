package ru.unc6.promeets.model.entity;

import javax.persistence.*;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "meet_notes", schema = "public", catalog = "promeets_db")
public class MeetNote {
    private long noteId;
    private String text;
    private Meet meet;
    private MeetTarget target;

    @Id
    @Column(name = "note_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
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

        MeetNote meetNote = (MeetNote) o;

        if (noteId != meetNote.noteId) return false;
        if (text != null ? !text.equals(meetNote.text) : meetNote.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (noteId ^ (noteId >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "meet_id", referencedColumnName = "meet_id", nullable = false)
    public Meet getMeet() {
        return meet;
    }

    public void setMeet(Meet meet) {
        this.meet = meet;
    }

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "target_id")
    public MeetTarget getTarget() {
        return target;
    }

    public void setTarget(MeetTarget target) {
        this.target = target;
    }
}
