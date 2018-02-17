package app.repository.dao;

import app.repository.entity.User;
import app.repository.etc.UserSearchParams;

import java.util.EnumSet;
import java.util.List;

public interface UserRepository extends CrudRepository<User>, Searchable<User, UserSearchParams> {

    enum Include {IMAGES, PROJECTS, LIKES, COMMENTS, BLOCKS, BOOKMARKS, REPORTS, SENT_MESSAGES, RECEIVED_MESSAGES};

    User get(long id, EnumSet<Include> includes);
    User getByEmail(String email);
    User getByUsername(String username);
}