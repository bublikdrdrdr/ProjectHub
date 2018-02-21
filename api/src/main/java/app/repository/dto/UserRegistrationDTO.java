package app.repository.dto;

import app.repository.entity.User;

import java.sql.Timestamp;

public class UserRegistrationDTO extends UserDTO {

    public String password;
    public String email;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(Long id, String username, String email, String name, String surname, Long photoId, Long lastOnline, Boolean blocked, String password) {
        super(id, username, name, surname, photoId, lastOnline, blocked);
        this.password = password;
        this.email = email;
    }

    public UserRegistrationDTO(User user, Long photoId, Boolean blocked, String password) {
        super(user, photoId, blocked);
        this.password = password;
    }

    public User convertToUser(Timestamp registered, Timestamp lastOnline){
        return new User(id, email, username, name, surname, password, registered, lastOnline);
    }
}
