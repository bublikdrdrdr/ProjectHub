package app.repository.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDTO {

    public Long id;
    public Long created;
    public Long posted;
    public Long author_id;
    public String subject;
    public String content;
    public List<ProjectAttachmentDTO> attachments;
    public Long likes;
    public Long dislikes;
    public Long comments;
    public Boolean liked;//by current user
    public Boolean disliked;

    public ProjectDTO() {
    }

    public ProjectDTO(Long id, Long created, Long posted, Long author_id, String subject, String content, List<ProjectAttachmentDTO> attachments, Long likes, Long dislikes, Long comments) {
        this.id = id;
        this.created = created;
        this.posted = posted;
        this.author_id = author_id;
        this.subject = subject;
        this.content = content;
        this.attachments = attachments;
        this.likes = likes;
        this.comments = comments;
    }
}
