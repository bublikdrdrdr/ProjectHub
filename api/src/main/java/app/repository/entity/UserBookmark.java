package app.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Bublik on 11-Dec-17.
 */
@Entity
@Table(name = "user_bookmarks")
public class UserBookmark {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private User bookmarked;

    @Column(nullable = false)
    private Timestamp added;

    public UserBookmark() {
    }

    public UserBookmark(Long id, User user, User bookmarked, Timestamp added) {
        this.id = id;
        this.user = user;
        this.bookmarked = bookmarked;
        this.added = added;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(User bookmarked) {
        this.bookmarked = bookmarked;
    }

    public Timestamp getAdded() {
        return added;
    }

    public void setAdded(Timestamp added) {
        this.added = added;
    }
}
