package app.entities.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

import static app.Properties.db.*;

/**
 * Created by Bublik on 11-Dec-17.
 */
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private User receiver;

    @Column(name = "deleted_by_receiver")
    private boolean deletedByReceiver;

    @Column(name = "deleted_by_sender")
    private boolean deletedBySender;

    @Column(length = messageLength, nullable = false)
    private String message;

    @Column(nullable = false)
    private Timestamp sent;

    @Column
    private boolean read;

    @Column(length = messageImageBytes)
    private byte[] image;

    public Message() {
    }

    public Message(Long id, User sender, User receiver, boolean deletedByReceiver, boolean deletedBySender, String message, Timestamp sent, boolean read, byte[] image) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.deletedByReceiver = deletedByReceiver;
        this.deletedBySender = deletedBySender;
        this.message = message;
        this.sent = sent;
        this.read = read;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public boolean isDeletedByReceiver() {
        return deletedByReceiver;
    }

    public void setDeletedByReceiver(boolean deletedByReceiver) {
        this.deletedByReceiver = deletedByReceiver;
    }

    public boolean isDeletedBySender() {
        return deletedBySender;
    }

    public void setDeletedBySender(boolean deletedBySender) {
        this.deletedBySender = deletedBySender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getSent() {
        return sent;
    }

    public void setSent(Timestamp sent) {
        this.sent = sent;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
