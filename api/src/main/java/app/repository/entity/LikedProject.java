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
    @Enumerated(value = EnumType.ORDINAL)
    private MarkType markType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    @JsonIgnore
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    @JsonIgnore
    private Project project;

    @Column(nullable = false)
    private Timestamp time;
}
