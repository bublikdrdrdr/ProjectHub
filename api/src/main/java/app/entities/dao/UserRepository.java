package app.entities.dao;

import app.entities.db.User;
import app.entities.etc.UserSearchParams;

import java.util.EnumSet;
import java.util.List;

public interface UserRepository {

    enum Include {IMAGES, PROJECTS, LIKES, COMMENTS, BLOCKS, BOOKMARKS, REPORTS, SENT_MESSAGES, RECEIVED_MESSAGES};

    User get(long id);
    User get(long id, EnumSet<Include> includes);
    User getByEmail(String email);
    User getByUsername(String username);
    long save(User user);
    List<User> search(UserSearchParams searchParams);
    long count(UserSearchParams searchParams);
    void remove(User user);
}
