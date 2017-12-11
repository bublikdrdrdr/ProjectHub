package app.entities.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

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

    @Column
    private Timestamp created;

    @ManyToOne
    @JoinColumn
    private User author;

    @Column(nullable = false)
    private String subject;

    @Column
    private String content;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonIgnore
    private Set<ProjectAttachment> projectAttachments;

    public Project() {
    }

    public Project(Long id, Timestamp created, User author, String subject, String content, Set<ProjectAttachment> projectAttachments) {
        this.id = id;
        this.created = created;
        this.author = author;
        this.subject = subject;
        this.content = content;
        this.projectAttachments = projectAttachments;
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

    public Set<ProjectAttachment> getProjectAttachments() {
        return projectAttachments;
    }

    public void setProjectAttachments(Set<ProjectAttachment> projectAttachments) {
        this.projectAttachments = projectAttachments;
    }
}
