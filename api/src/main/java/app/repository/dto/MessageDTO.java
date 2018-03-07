package app.repository.dto;

import app.repository.entity.Message;
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

    public MessageDTO(Message message, long interlocutorId) {
        this(message.getId(),message.getMessage(), interlocutorId, message.getImage()!=null, message.getSent().getTime(), message.isSeen(), message.getReceiver().getId()==interlocutorId);
    }
}
