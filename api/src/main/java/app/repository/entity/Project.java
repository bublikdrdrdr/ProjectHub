package app.repository.entity;

import app.repository.etc.EntityParams;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Bublik on 21-Nov-17.
 */
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Timestamp created;

    @Column(nullable = false)
    private Timestamp posted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    @JsonIgnore
    private User author;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, length = EntityParams.projectContentLength)
    private String content;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonIgnore
    private List<ProjectAttachment> attachments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonIgnore
    private List<LikedProject> likes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonIgnore
    private List<ProjectComment> projectComments;


    public Project() {
    }

    public Project(Long id, Timestamp created, User author, String subject, String content, List<ProjectAttachment> attachments) {
        this.id = id;
        this.created = created;
        this.author = author;
        this.subject = subject;
        this.content = content;
        this.attachments = attachments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ProjectAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ProjectAttachment> attachments) {
        this.attachments = attachments;
    }
}
