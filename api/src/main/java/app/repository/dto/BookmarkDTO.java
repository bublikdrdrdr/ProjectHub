package app.repository.dto;

public class BookmarkDTO {

    public UserDTO user;
    public long added;

    public BookmarkDTO() {
    }

    public BookmarkDTO(UserDTO user, long added) {
        this.user = user;
        this.added = added;
    }
}
