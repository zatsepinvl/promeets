package ru.unc6.promeets.model.entity;

import javax.persistence.*;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "meet_aims", schema = "public", catalog = "promeets_db")
public class MeetAim {
    private long aimId;
    private String value;
    private Meet meet;

    @Id
    @Column(name = "aim_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getAimId() {
        return aimId;
    }

    public void setAimId(long targetId) {
        this.aimId = targetId;
    }

    @Basic
    @Column(name = "value", nullable = false, length = -1)
    public String getValue() {
        return value;
    }

    public void setValue(String text) {
        this.value = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeetAim that = (MeetAim) o;

        if (aimId != that.aimId) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (aimId ^ (aimId >>> 32));
        result = 31 * result + (value != null ? value.hashCode() : 0);
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
}
