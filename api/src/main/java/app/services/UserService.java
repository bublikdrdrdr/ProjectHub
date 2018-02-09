package app.services;

import app.entities.db.User;
import app.entities.db.UserBlock;
import app.entities.etc.SearchParams;
import app.entities.etc.UserSearchParams;
import app.exceptions.FieldAvailabilityCheckException;
import app.exceptions.UserAlreadyExistsException;

import java.util.IllegalFormatException;
import java.util.List;

/**
 * Created by Bublik on 22-Dec-17.
 */
public interface UserService {

    User get(long id);
    void register(User user) throws UserAlreadyExistsException, IllegalFormatException;
    boolean checkFieldAvailable(DataType userField) throws FieldAvailabilityCheckException;
    User login(String email, String password);
    void update(User user) throws IllegalFormatException;
    List<User> search(UserSearchParams searchParams);
    long countSearch(UserSearchParams searchParams);
    void block(UserBlock userBlock);
    void bookmark(long id);
    List<User> getBookmarks(SearchParams searchParams);
    void removeBookmark(long id);

}