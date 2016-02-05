package ru.unc6.promeets.model.entity;

import javax.persistence.*;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "group_types", schema = "public", catalog = "promeets_db")
public class GroupType {
    private long typeId;
    private String name;

    @Id
    @Column(name = "type_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
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

        GroupType that = (GroupType) o;

        if (typeId != that.typeId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (typeId ^ (typeId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
