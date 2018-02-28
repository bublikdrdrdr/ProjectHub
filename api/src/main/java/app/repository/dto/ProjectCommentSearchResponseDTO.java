package app.repository.dto;

import java.util.List;

public class ProjectCommentSearchResponseDTO extends ResponseDTO<ProjectCommentDTO> {

    public ProjectCommentSearchResponseDTO() {
    }

    public ProjectCommentSearchResponseDTO(Long count) {
        super(count);
    }

    public ProjectCommentSearchResponseDTO(Long count, List<ProjectCommentDTO> items) {
        super(count, items);
    }
}
