package com.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "card_files", schema = "public", catalog = "promeets_db")
public class CardFile {
    private CardFilePK cardFilePK;

    public CardFile() {
        cardFilePK = new CardFilePK();
    }

    @EmbeddedId
    @JsonIgnore
    public CardFilePK getCardFilePK() {
        return cardFilePK;
    }

    public void setCardFilePK(CardFilePK cardFilePK) {
        this.cardFilePK = cardFilePK;
    }

    @Transient
    @JsonIgnore
    public Card getCard() {
        return cardFilePK.getCard();
    }

    @JsonProperty
    public void setCard(Card card) {
        cardFilePK.setCard(card);
    }

    @Transient
    public File getFile() {
        return cardFilePK.getFile();
    }

    public void setFile(File file) {
        cardFilePK.setFile(file);
    }
}
