package app.entities.db;


import app.Properties;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Created by Bublik on 10-Nov-17.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(length = Properties.db.nameLength, nullable = false)
    private String name;

    @Column(length = Properties.db.surnameLength, nullable = false)
    private String surname;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "password_salt", nullable = false)
    @JsonIgnore
    private String passwordSalt;

    @OneToOne(fetch = FetchType.LAZY)//TODO: test (without mappedBy)
    @JsonIgnore
    private Image image;

    @Column
    private Timestamp registered;

    @Column(name = "last_login")
    private Timestamp lastOnline;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Project> projects;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<LikedProject> likedProjects;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProjectComment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserBlock> blocks;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserBookmark> bookmarks;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Report> reports;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Message> receivedMessages;


    public User() {
    }

    public User(String email, String username, String name, String surname, String password, String passwordSalt, Timestamp registered, Timestamp lastOnline) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.registered = registered;
        this.lastOnline = lastOnline;
    }

    public User(Long id, String email, String username, String name, String surname, String password, String passwordSalt, Image image,
                Timestamp registered, Timestamp lastOnline, Set<Project> projects, Set<LikedProject> likedProjects,
                Set<ProjectComment> comments, Set<UserBlock> blocks, Set<UserBookmark> bookmarks, Set<Report> reports,
                List<Message> sentMessages, List<Message> receivedMessages) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.image = image;
        this.registered = registered;
        this.lastOnline = lastOnline;
        this.projects = projects;
        this.likedProjects = likedProjects;
        this.comments = comments;
        this.blocks = blocks;
        this.bookmarks = bookmarks;
        this.reports = reports;
        this.sentMessages = sentMessages;
        this.receivedMessages = receivedMessages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Timestamp getRegistered() {
        return registered;
    }

    public void setRegistered(Timestamp registered) {
        this.registered = registered;
    }

    public Timestamp getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Timestamp lastOnline) {
        this.lastOnline = lastOnline;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<LikedProject> getLikedProjects() {
        return likedProjects;
    }

    public void setLikedProjects(Set<LikedProject> likedProjects) {
        this.likedProjects = likedProjects;
    }

    public Set<ProjectComment> getComments() {
        return comments;
    }

    public void setComments(Set<ProjectComment> comments) {
        this.comments = comments;
    }

    public Set<UserBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<UserBlock> blocks) {
        this.blocks = blocks;
    }

    public Set<UserBookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Set<UserBookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }
}
