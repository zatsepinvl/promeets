package ru.unc6.promeets.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vladimir on 30.01.2016.
 */
@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {
    private long userId;
    private String email;
    private String phone;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String company;
    private String position;
    private List<Meet> meets;
    private File image;
    private List<Group> groups;
    private List<Chat> chats;
    private List<UserMeet> meetSettings;

    @Id
    @Column(name = "user_id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "email", nullable = false, length = -1)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone", length = -1)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "password", nullable = false, length = -1)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = -1)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = -1)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "address", length = -1)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "company", length = -1)
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Basic
    @Column(name = "position", length = -1)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (company != null ? !company.equals(user.company) : user.company != null) return false;
        if (position != null ? !position.equals(user.position) : user.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @ManyToMany
    @JoinTable(name = "user_meets", catalog = "promeets_db", schema = "public", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "meet_id", referencedColumnName = "meet_id", nullable = false))
    public List<Meet> getMeets() {
        return meets;
    }

    public void setMeets(List<Meet> meets) {
        this.meets = meets;
    }

    @ManyToOne
    @JoinColumn(name = "image_id", referencedColumnName = "file_id")
    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    @ManyToMany
    @JoinTable(name = "user_groups", catalog = "promeets_db", schema = "public", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false))
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @ManyToMany
    @JoinTable(name = "user_chats", catalog = "promeets_db", schema = "public", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "chat_id", nullable = false))
    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    /*@OneToMany
    public List<UserMeet> getMeetSettings() {
        return meetSettings;
    }

    public void setMeetSettings(List<UserMeet> meetSettings) {
        meetSettings = meetSettings;
    }
    */
}
