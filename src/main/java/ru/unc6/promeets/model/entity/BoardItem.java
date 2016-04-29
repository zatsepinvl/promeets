package ru.unc6.promeets.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "board_items", schema = "public", catalog = "promeets_db")
public class BoardItem implements Serializable {
    private long itemId;
    private String data;
    private Board board;
    private MeetTask aim;
    private File file;

    @Id
    @Column(name = "item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "data", length = -1)
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardItem that = (BoardItem) o;

        if (itemId != that.itemId) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }


    @Transient
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    public MeetTask getAim() {
        return aim;
    }

    public void setAim(MeetTask target) {
        this.aim = target;
    }

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "file_id")
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
