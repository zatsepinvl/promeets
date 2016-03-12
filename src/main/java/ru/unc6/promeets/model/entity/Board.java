package ru.unc6.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "boards")
public class Board {
    private long boardId;
    private String title;
    private Meet meet;

    @Id
    @Column(name = "board_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    @Basic
    @Column(name = "title")
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

        Board board = (Board) o;

        if (boardId != board.boardId) return false;
        if (title != null ? !title.equals(board.title) : board.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (boardId ^ (boardId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @OneToOne(mappedBy = "board")
    public Meet getMeet() {
        return meet;
    }

    public void setMeet(Meet meet) {
        this.meet = meet;
    }
}
