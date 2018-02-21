package app.repository.dto;

import app.repository.entity.UserBlock;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockDTO {

    public Long id;
    public Long start;
    public Long end;
    public Long user_id;
    public Long admin_id;
    public Integer canceled_by_id;
    public UserDTO user;
    public UserDTO admin;
    public UserDTO canceled_by;
    public String comment;
    public Boolean active;

    public BlockDTO() {
    }

    public BlockDTO(Long id, Long start, Long end, Long user_id, Long admin_id, Integer canceled_by_id, String comment) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.user_id = user_id;
        this.admin_id = admin_id;
        this.canceled_by_id = canceled_by_id;
        this.comment = comment;
    }

    public BlockDTO(Long id, Long start, Long end, UserDTO user, UserDTO admin, UserDTO canceled_by, String comment, Boolean active) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.user = user;
        this.admin = admin;
        this.canceled_by = canceled_by;
        this.comment = comment;
        this.active = active;
    }

    public BlockDTO(Long id, Long start, Long end, Long user_id, Long admin_id, Integer canceled_by_id, UserDTO user, UserDTO admin, UserDTO canceled_by, String comment, Boolean active) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.user_id = user_id;
        this.admin_id = admin_id;
        this.canceled_by_id = canceled_by_id;
        this.user = user;
        this.admin = admin;
        this.canceled_by = canceled_by;
        this.comment = comment;
        this.active = active;
    }
}
