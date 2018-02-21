package app.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Bublik on 11-Dec-17.
 */
@Entity
@Table(name = "user_blocks")
public class UserBlock {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Timestamp start;

    @Column(nullable = false)
    private Timestamp end;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    private User admin;

    @Column
    private String comment;

    @Column
    private boolean canceled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User canceledBy;

    public UserBlock() {
    }

    public UserBlock(Long id, Timestamp start, Timestamp end, User user, User admin, String comment, Boolean canceled, User canceledBy) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.user = user;
        this.admin = admin;
        this.comment = comment;
        this.canceled = canceled;
        this.canceledBy = canceledBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public User getCanceledBy() {
        return canceledBy;
    }

    public void setCanceledBy(User canceledBy) {
        this.canceledBy = canceledBy;
    }
}
