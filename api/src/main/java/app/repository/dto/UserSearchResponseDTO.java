package app.repository.dto;

import java.util.List;

public class UserSearchResponseDTO extends ResponseDTO<UserDTO>{

    public UserSearchResponseDTO() {
    }

    public UserSearchResponseDTO(Long count, List<UserDTO> items) {
        super(count, items);
    }
}
