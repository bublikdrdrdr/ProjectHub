package app.entities.db;


import app.Properties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.sql.Timestamp;
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

    @Column(length = 104857600)
    @Lazy
    @JsonIgnore
    private byte[] image;

    @Column
    private Timestamp registered;

    @Column(name = "last_login")
    private Timestamp lastOnline;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Project> projects;

    public User() {
    }

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String email, String name, String surname, String password, String passwordSalt, Timestamp registered, Timestamp lastOnline) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.registered = registered;
        this.lastOnline = lastOnline;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
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
}
