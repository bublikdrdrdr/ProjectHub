package app.repository.dto;

import app.repository.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeId;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    public Long id;
    public String username;
    public String name;
    public String surname;
    public Long image_id;
    public Long last_online;
    public Boolean blocked;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String name, String surname, Long image_id, Long last_online, Boolean blocked) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.image_id = image_id;
        this.last_online = last_online;
        this.blocked = blocked;
    }

    public UserDTO(User user, Long image_id, Boolean blocked){
        this(user.getId(), user.getUsername(), user.getName(), user.getSurname(), image_id, (user.getLastOnline()==null?null:user.getLastOnline().getTime()), blocked);
    }
}
