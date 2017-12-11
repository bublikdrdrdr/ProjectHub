package app.entities.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Bublik on 10-Dec-17.
 */

@Entity
@Table(name="project_attachments")
public class ProjectAttachment {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Project project;

    @OneToOne(optional = false)
    private AttachmentType attachmentType;

    @Column
    private String textValue;

    @Column
    private byte[] blobValue;

    public ProjectAttachment() {
    }

    public ProjectAttachment(Long id, Project project, AttachmentType attachmentType, String textValue, byte[] blobValue) {
        this.id = id;
        this.project = project;
        this.attachmentType = attachmentType;
        this.textValue = textValue;
        this.blobValue = blobValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public byte[] getBlobValue() {
        return blobValue;
    }

    public void setBlobValue(byte[] blobValue) {
        this.blobValue = blobValue;
    }
}
