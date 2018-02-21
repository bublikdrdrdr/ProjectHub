package app.repository.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDTO {

    public Long id;
    public String message;
    public Long interlocutor;
    public Boolean attachment;
    public Long time;
    public Boolean seen;
    public Boolean out;

    public MessageDTO() {
    }

    public MessageDTO(Long id, String message, Long interlocutor, Boolean attachment, Long time, Boolean seen, Boolean out) {
        this.id = id;
        this.message = message;
        this.interlocutor = interlocutor;
        this.attachment = attachment;
        this.time = time;
        this.seen = seen;
        this.out = out;
    }
}
