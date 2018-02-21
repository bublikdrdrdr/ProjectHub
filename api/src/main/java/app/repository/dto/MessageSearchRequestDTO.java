package app.repository.dto;

public class MessageSearchRequestDTO extends PaginationDTO {

    public Long interlocutor;
    public Long from_message;

    public MessageSearchRequestDTO() {
    }

    public MessageSearchRequestDTO(Integer first, Integer count, Long interlocutor, Long from_message) {
        super(null, null, first, count);
        this.interlocutor = interlocutor;
        this.from_message = from_message;
    }
}