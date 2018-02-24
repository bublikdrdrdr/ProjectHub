package app.repository.dto;

import java.util.List;

public class ProjectSearchResponseDTO extends ResponseDTO<ProjectDTO> {

    public ProjectSearchResponseDTO() {
    }

    public ProjectSearchResponseDTO(Long count) {
        super(count);
    }

    public ProjectSearchResponseDTO(Long count, List<ProjectDTO> items) {
        super(count, items);
    }
}
