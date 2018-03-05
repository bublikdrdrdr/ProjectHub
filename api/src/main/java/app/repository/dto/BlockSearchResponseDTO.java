package app.repository.dto;

import java.util.List;

public class BlockSearchResponseDTO extends ResponseDTO<BlockDTO> {

    public BlockSearchResponseDTO() {
    }

    public BlockSearchResponseDTO(Long count, List<BlockDTO> items) {
        super(count, items);
    }
}
