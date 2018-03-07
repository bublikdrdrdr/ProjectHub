package app.repository.dto;

public class MessageSearchRequestDTO extends PaginationDTO {

    public Long interlocutor;
    public Boolean unreadOnly;
    public String message;
    public Long from_message;//wtf?

    public MessageSearchRequestDTO() {
    }

    public MessageSearchRequestDTO(Boolean desc, Integer first, Integer count, Long interlocutor, Boolean unreadOnly, String message) {
        super(null, desc, first, count);
        this.interlocutor = interlocutor;
        this.unreadOnly = unreadOnly;
        this.message = message;
    }


}