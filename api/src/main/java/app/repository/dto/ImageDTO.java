package app.repository.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageDTO {

    public Long id;
    public Long owner_id;
    public Long added;

    public ImageDTO() {
    }

    public ImageDTO(Long id, Long owner_id, Long added) {
        this.id = id;
        this.owner_id = owner_id;
        this.added = added;
    }
}
