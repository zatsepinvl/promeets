package ru.unc6.promeets.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "boards", schema = "public", catalog = "promeets_db")
public class Board implements Serializable {
    private long boardId;
    private String title;
    private String data;
    private Meet meet;
    private User editor;

    @Id
    @Column(name = "board_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long pageId) {
        this.boardId = pageId;
    }


    @Basic
    @Column(name = "title", length = -1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board that = (Board) o;

        if (boardId != that.boardId) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (boardId ^ (boardId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }


    @Basic
    @Column(name = "data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @ManyToOne
    @JoinColumn(name = "meet_id", referencedColumnName = "meet_id")
    public Meet getMeet() {
        return meet;
    }

    public void setMeet(Meet meet) {
        this.meet = meet;
    }

    @ManyToOne
    @JoinColumn(name = "editor_id", referencedColumnName = "user_id")
    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }
}
