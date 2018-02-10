package app.service;

import app.repository.entity.User;
import app.repository.entity.UserBlock;
import app.repository.etc.SearchParams;
import app.repository.etc.UserSearchParams;
import app.exception.FieldAvailabilityCheckException;
import app.exception.UserAlreadyExistsException;

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