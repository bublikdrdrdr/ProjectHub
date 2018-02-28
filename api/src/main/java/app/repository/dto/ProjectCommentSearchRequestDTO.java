package app.repository.dto;

import app.repository.entity.Project;
import app.repository.etc.CommentSearchParams;

public class ProjectCommentSearchRequestDTO extends PaginationDTO {

    public Long project;
    public String message;

    public ProjectCommentSearchRequestDTO() {
    }

    public ProjectCommentSearchRequestDTO(Boolean desc, Integer first, Integer count, Long project) {
        super(null, desc, first, count);
        this.project = project;
    }

    public CommentSearchParams getSearchParams(Project project){
        return new CommentSearchParams(desc, first, count, project, message);
    }
}
