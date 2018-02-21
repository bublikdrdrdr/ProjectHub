package app.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Bublik on 11-Dec-17.
 */
@Entity
@Table(name = "liked_projects")
public class LikedProject {

    public enum MarkType {LIKE, DISLIKE}

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MarkType markType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    private Project project;

    @Column(nullable = false)
    private Timestamp time;

    public LikedProject() {
    }

    public LikedProject(MarkType markType, User owner, Project project, Timestamp time) {
        this.markType = markType;
        this.owner = owner;
        this.project = project;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MarkType getMarkType() {
        return markType;
    }

    public void setMarkType(MarkType markType) {
        this.markType = markType;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
