package app.service;

import app.exception.FieldAvailabilityCheckException;
import app.exception.InvalidFieldFormatException;
import app.exception.InvalidTypeException;
import app.exception.UserAlreadyExistsException;
import app.repository.dao.UserRepository;
import app.repository.entity.User;
import app.repository.entity.UserBlock;
import app.repository.etc.SearchParams;
import app.repository.etc.UserSearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.exception.UserAlreadyExistsException.*;

import java.util.EnumSet;
import java.util.IllegalFormatException;
import java.util.List;

import static app.service.DataType.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDataChecker userDataChecker;

    @Override
    public User get(long id) {
        return userRepository.get(id);
    }

    @Override
    public void register(User user) throws UserAlreadyExistsException, InvalidFieldFormatException {
        EnumSet<ConflictField> conflicts = checkConflicts(user);
        if (conflicts.size()>0) throw new UserAlreadyExistsException(conflicts);
        EnumSet<DataType> dataChecks = checkUserData(user);
        if (dataChecks.size()>0) throw new InvalidFieldFormatException(dataChecks);
        //TODO: other
    }

    @Override
    public boolean checkFieldAvailable(DataType userField, String value) throws FieldAvailabilityCheckException {
        try {
            switch (userField) {
                case EMAIL:
                    return (userRepository.getByEmail(value) == null);
                case NICKNAME:
                    return (userRepository.getByUsername(value) == null);
                default:
                    throw new InvalidTypeException(userField);
            }
        } catch (Exception e){
            throw new FieldAvailabilityCheckException(userField, e);
        }
    }

    @Override
    public User login(String email, String password) {
        return null;
    }

    @Override
    public void update(User user) throws IllegalFormatException {

    }

    @Override
    public List<User> search(UserSearchParams searchParams) {
        return null;
    }

    @Override
    public long countSearch(UserSearchParams searchParams) {
        return 0;
    }

    @Override
    public void block(UserBlock userBlock) {

    }

    @Override
    public void bookmark(long id) {

    }

    @Override
    public List<User> getBookmarks(SearchParams searchParams) {
        return null;
    }

    @Override
    public void removeBookmark(long id) {

    }

    private EnumSet<ConflictField> checkConflicts(User user){
        EnumSet<ConflictField> conflicts = EnumSet.noneOf(ConflictField.class);
        if (checkFieldAvailable(DataType.NICKNAME, user.getUsername())) conflicts.add(ConflictField.USERNAME);
        if (checkFieldAvailable(DataType.EMAIL, user.getEmail())) conflicts.add(ConflictField.EMAIL);
        return conflicts;
    }

    private EnumSet<DataType> checkUserData(User user){
        EnumSet<DataType> set = EnumSet.noneOf(DataType.class);
        if (!userDataChecker.check(NAME, user.getName())) set.add(NAME);
        if (!userDataChecker.check(SURNAME, user.getSurname())) set.add(SURNAME);
        if (!userDataChecker.check(EMAIL, user.getEmail())) set.add(EMAIL);
        if (!userDataChecker.check(NICKNAME, user.getUsername())) set.add(NICKNAME);
        if (!userDataChecker.check(PASSWORD, user.getPassword())) set.add(PASSWORD);
        return set;
    }
}
