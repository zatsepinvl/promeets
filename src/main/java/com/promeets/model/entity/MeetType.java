package com.promeets.model.entity;

import javax.persistence.*;

/**
 * Created by Vladimir on 05.02.2016.
 */
@Entity
@Table(name = "meet_types", schema = "public", catalog = "promeets_db")
public class MeetType {
    private long typeId;
    private String name;

    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "name")
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

        MeetType meetType = (MeetType) o;

        if (typeId != meetType.typeId) return false;
        if (name != null ? !name.equals(meetType.name) : meetType.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (typeId ^ (typeId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
