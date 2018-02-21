package app.repository.dto;

public class LoginResponseDTO {

    public Long id;
    public String token;//TODO: change to actual because I don't know what kind of data will be used for authentication

    public LoginResponseDTO(Long id, String token) {
        this.id = id;
        this.token = token;
    }
}
