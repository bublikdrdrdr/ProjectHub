package app.repository.dto;

import java.util.List;

public class MessageSearchResponseDTO extends ResponseDTO<MessageDTO> {

    public MessageSearchResponseDTO() {
    }

    public MessageSearchResponseDTO(Long count, List<MessageDTO> items) {
        super(count, items);
    }
}
