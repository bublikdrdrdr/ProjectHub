package app.services;

import app.entities.db.User;
import app.entities.rest.SearchParams;
import app.exceptions.FieldAvailabilityCheckException;
import app.exceptions.UserAlreadyExistsException;

import java.util.IllegalFormatException;
import java.util.List;

/**
 * Created by Bublik on 22-Dec-17.
 */
public interface UserService {

    void registerUser(User user) throws UserAlreadyExistsException, IllegalFormatException;
    boolean checkFieldAvailable(DataType userField) throws FieldAvailabilityCheckException;
    User login(String email, String password);
    void updateProfile(User user) throws IllegalFormatException;
    List<User> searchUsers(SearchParams searchParams);
    long countSearchUsers(SearchParams searchParams);

}