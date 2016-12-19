package com.promeets.model.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "files")
@XmlRootElement
public class File implements Serializable, Cloneable {

    public enum ImageSize {
        SMALL,
        MEDIUM,
        LARGE,
        ORIGINAL
    }

    private long fileId;
    private String small;
    private String medium;
    private String large;
    private String original;
    private String name;
    private long time;


    @Id
    @Column(name = "file_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        if (fileId != file.fileId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (fileId ^ (fileId >>> 32));
        return result;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public File clone() {
        File file = new File();
        file.setSmall(this.small);
        file.setMedium(this.medium);
        file.setLarge(this.large);
        file.setOriginal(this.original);
        file.setName(this.name);
        file.setTime(this.getTime());
        return file;
    }


    @Column(name = "small")
    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    @Column(name = "medium")
    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    @Column(name = "large")
    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    @Column(name = "original")
    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public void setUrlByImageSize(ImageSize size, String url) {
        switch (size) {
            case SMALL:
                setSmall(url);
                break;
            case MEDIUM:
                setMedium(url);
                break;
            case LARGE:
                setLarge(url);
                break;
            case ORIGINAL:
                setOriginal(url);
                break;
            default:
                setOriginal(url);
        }
    }

    @Column(name = "time")
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Transient
    public String getUrl() {
        return original;
    }
}
