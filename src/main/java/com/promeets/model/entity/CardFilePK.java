package com.promeets.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class CardFilePK implements Serializable {
    private Card card;
    private File file;

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "card_id")
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "file_id", referencedColumnName = "file_id")
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
