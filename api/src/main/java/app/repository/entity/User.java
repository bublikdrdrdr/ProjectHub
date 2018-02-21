package app.repository.entity;


import app.repository.etc.EntityParams;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.List;

/**
 * Created by Bublik on 10-Nov-17.
 */
@Entity
@Table(name = "users")
public class User {

    //TODO: add boolean admin field

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(length = EntityParams.nameLength, nullable = false)
    private String name;

    @Column(length = EntityParams.surnameLength, nullable = false)
    private String surname;

    @Column(nullable = false)
    private String password;

    /*@Column(name = "password_salt", nullable = false)
    private String passwordSalt;*/

    @OneToOne(fetch = FetchType.LAZY)
    private Image image;

    @Column(nullable = false)
    private Timestamp registered;

    @Column(name = "last_login")
    private Timestamp lastOnline;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Project> projects;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<LikedProject> likedProjects;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<ProjectComment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserBlock> blocks;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserBookmark> bookmarks;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Report> reports;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Message> receivedMessages;


    public User() {
    }

    public User(String email, String username, String name, String surname, String password, Timestamp registered, Timestamp lastOnline) {
        this(null, email, username, name, surname, password, registered, lastOnline);
    }

    public User(Long id, String email, String username, String name, String surname, String password, Timestamp registered, Timestamp lastOnline) {
        this(id, email, username, name, surname, password, null, registered, lastOnline, null, null, null, null, null, null, null, null);
    }

    public User(Long id, String email, String username, String name, String surname, String password, Image image,
                Timestamp registered, Timestamp lastOnline, List<Project> projects, List<LikedProject> likedProjects,
                List<ProjectComment> comments, List<UserBlock> blocks, List<UserBookmark> bookmarks, List<Report> reports,
                List<Message> sentMessages, List<Message> receivedMessages) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<LikedProject> getLikedProjects() {
        return likedProjects;
    }

    public void setLikedProjects(List<LikedProject> likedProjects) {
        this.likedProjects = likedProjects;
    }

    public List<ProjectComment> getComments() {
        return comments;
    }

    public void setComments(List<ProjectComment> comments) {
        this.comments = comments;
    }

    public List<UserBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<UserBlock> blocks) {
        this.blocks = blocks;
    }

    public List<UserBookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<UserBookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
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

    public boolean isBlocked(Timestamp now) {
        for (UserBlock userBlock: getBlocks()){
            if (userBlock.getStart().before(now) && userBlock.getEnd().after(now) && !userBlock.getCanceled()) return true;
        }
        return false;
    }
}
