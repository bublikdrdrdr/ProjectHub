package app.repository.dto;

public class ProjectCommentDTO {

    public Long id;
    public Long user;
    public Long project;
    public Long posted;
    public String message;

    public ProjectCommentDTO() {
    }

    public ProjectCommentDTO(Long id, Long user, Long project, Long posted, String message) {
        this.id = id;
        this.user = user;
        this.project = project;
        this.posted = posted;
        this.message = message;
    }
}
