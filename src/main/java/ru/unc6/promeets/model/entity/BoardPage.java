package ru.unc6.promeets.model.entity;

import javax.persistence.*;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "board_pages", schema = "public", catalog = "promeets_db")
public class BoardPage {
    private long pageId;
    private short number;
    private String title;
    private Board board;

    @Id
    @Column(name = "page_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getPageId() {
        return pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    @Basic
    @Column(name = "number", nullable = false)
    public short getNumber() {
        return number;
    }

    public void setNumber(short number) {
        this.number = number;
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

        BoardPage that = (BoardPage) o;

        if (pageId != that.pageId) return false;
        if (number != that.number) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (pageId ^ (pageId >>> 32));
        result = 31 * result + (int) number;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "board_id", referencedColumnName = "board_id", nullable = false)
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


}
