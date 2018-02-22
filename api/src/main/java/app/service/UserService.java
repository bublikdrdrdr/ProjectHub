package app.service;

import app.exception.*;
import app.repository.dto.*;
import app.repository.entity.User;
import app.repository.entity.UserBlock;
import app.repository.etc.SearchParams;
import app.repository.etc.UserSearchParams;

import javax.persistence.EntityNotFoundException;

/**
 * Created by Bublik on 22-Dec-17.
 */
public interface UserService {

    UserDTO get(long id) throws EntityNotFoundException;
    long register(UserRegistrationDTO user) throws UserAlreadyExistsException, InvalidFieldFormatException;
    boolean checkFieldAvailable(FieldCheckDTO check) throws FieldAvailabilityCheckException;
    LoginResponseDTO login(LoginRequestDTO request) throws AuthenticationException;
    void update(UserRegistrationDTO user) throws InvalidFieldFormatException, SetValueException;
    UserSearchResponseDTO search(UserSearchRequestDTO request);
    void bookmark(long id) throws EntityNotFoundException, EntityAlreadyExistsException;
    BookmarkSearchResponseDTO getBookmarks(BookmarkSearchRequestDTO request);
    void removeBookmark(long id);
    void updateOnline();
}