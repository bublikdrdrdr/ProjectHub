package app.repository.dto;

import java.util.List;

public class ReportSearchResponseDTO extends ResponseDTO<ReportDTO> {
    public ReportSearchResponseDTO() {
    }

    public ReportSearchResponseDTO(Long count, List<ReportDTO> items) {
        super(count, items);
    }
}
