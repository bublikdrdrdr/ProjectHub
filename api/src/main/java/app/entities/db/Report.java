package app.entities.db;

import app.entities.etc.EntityParams;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Bublik on 12-Dec-17.
 */

@Entity
@Table(name = "reports")
public class Report {

    public enum ReportType{BUG, USER_BLOCK, OWN_BLOCK, PROJECT, COMMENT}

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private ReportType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private User sender;

    @Column(length = 500)
    private String url;

    @Column(length = EntityParams.messageLength)
    private String message;

    @Column
    private Timestamp sent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private User admin;

    @Column
    private boolean closed;

    public Report() {
    }

    public Report(Long id, ReportType type, User sender, String url, String message, Timestamp sent, User admin, boolean closed) {
        this.id = id;
        this.type = type;
        this.sender = sender;
        this.url = url;
        this.message = message;
        this.sent = sent;
        this.admin = admin;
        this.closed = closed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getSent() {
        return sent;
    }

    public void setSent(Timestamp sent) {
        this.sent = sent;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
