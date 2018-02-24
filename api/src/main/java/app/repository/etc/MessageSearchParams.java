package app.repository.etc;

import app.repository.entity.User;
import com.sun.istack.internal.NotNull;

public class MessageSearchParams extends SearchParams {

    public User owner;
    public User interlocutor;
    public boolean includeRemoved;
    public boolean unreadOnly;
    public String message;

    public MessageSearchParams(Boolean desc, Integer first, Integer count, @NotNull User owner, User interlocutor, Boolean includeRemoved, Boolean unreadOnly, String message) {
        super(null, desc, first, count);
        this.owner = owner;
        this.interlocutor = interlocutor;
        this.includeRemoved = nullToDefault(includeRemoved, false);
        this.unreadOnly = nullToDefault(unreadOnly, false);
        this.message = message;
    }
}