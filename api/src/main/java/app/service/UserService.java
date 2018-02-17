package app.service;

import app.exception.InvalidFieldFormatException;
import app.exception.SetValueException;
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
    void register(User user) throws UserAlreadyExistsException, InvalidFieldFormatException;
    boolean checkFieldAvailable(DataType userField, String value) throws FieldAvailabilityCheckException;
    User login(String email, String password);
    void update(User user) throws InvalidFieldFormatException, SetValueException;
    List<User> search(UserSearchParams searchParams);
    long countSearch(UserSearchParams searchParams);
    void block(UserBlock userBlock);
    void bookmark(long id);
    List<User> getBookmarks(SearchParams searchParams);
    void removeBookmark(long id);
    void updateOnline(long id);

}