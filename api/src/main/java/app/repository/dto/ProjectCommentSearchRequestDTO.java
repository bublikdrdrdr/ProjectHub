package app.repository.dto;

public class ProjectCommentSearchRequestDTO extends PaginationDTO {

    public Long project;
    public String message;

    public ProjectCommentSearchRequestDTO() {
    }

    public ProjectCommentSearchRequestDTO(Boolean desc, Integer first, Integer count, Long project) {
        super(null, desc, first, count);
        this.project = project;
    }
}
