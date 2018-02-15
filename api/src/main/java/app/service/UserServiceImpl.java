package app.service;

import app.exception.*;
import app.repository.dao.UserRepository;
import app.repository.entity.User;
import app.repository.entity.UserBlock;
import app.repository.etc.SearchParams;
import app.repository.etc.UserSearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import app.exception.UserAlreadyExistsException.*;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Date;
import java.util.EnumSet;
import java.util.IllegalFormatException;
import java.util.List;

import static app.service.DataType.*;

@Service
@PropertySource("service.properties")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserDataChecker userDataChecker;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDataChecker userDataChecker, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDataChecker = userDataChecker;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User get(long id) {
        return userRepository.get(id);
    }

    @Override
    public void register(User user) throws UserAlreadyExistsException, InvalidFieldFormatException {
        EnumSet<DataType> dataChecks = checkUserData(user);
        if (dataChecks.size()>0) throw new InvalidFieldFormatException(dataChecks);
        EnumSet<ConflictField> conflicts = checkConflicts(user);
        if (conflicts.size()>0) throw new UserAlreadyExistsException(conflicts);
        Timestamp now = new Timestamp(new Date().getTime());
        user = new User(user.getEmail(), user.getUsername(), user.getName(), user.getSurname(), user.getPassword(), user.getPasswordSalt(), now, now);
        userRepository.save(user);
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
        try {
            User user = userRepository.getByEmail(email);
            if (passwordEncoder.matches(password, user.getPassword()))
                return user; else return null;
        } catch (NullPointerException e){
            return null;
        }
    }

    @Value("${permission.can_update_email:false}")
    public boolean canUpdateEmail;

    @Value("${permission.can_update_username:false}")
    public boolean canUpdateUsername;

    @Override
    public void update(User user) throws InvalidFieldFormatException {
        User dbUser = userRepository.get(user.getId());
        EnumSet<DataType> errors = checkUserData(user, true);
        if (errors.size()>0) throw new InvalidFieldFormatException(errors);
        if (!canUpdateEmail&&user.getEmail()!=null&&!user.getEmail().equals(dbUser.getEmail())) throw new SetValueException("Update email not allowed");
        if (!canUpdateUsername&&user.getUsername()!=null&&!user.getUsername().equals(dbUser.getUsername())) throw new SetValueException("Update username not allowed");
        if (canUpdateEmail&&user.getEmail()!=null) dbUser.setEmail(user.getEmail());
        if (canUpdateUsername&&user.getUsername()!=null) dbUser.setUsername(user.getUsername());
        if (user.getName()!=null) dbUser.setName(user.getName());
        if (user.getSurname()!=null) dbUser.setSurname(user.getSurname());
        if (user.getPassword()!=null) dbUser.setPassword(user.getPassword());
        userRepository.save(user);
    }

    @Override
    public List<User> search(UserSearchParams searchParams) {
        return userRepository.search(searchParams);
    }

    @Override
    public long countSearch(UserSearchParams searchParams) {
        return userRepository.count(searchParams);
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
        return checkUserData(user, false);
    }

    private EnumSet<DataType> checkUserData(User user, boolean ignoreNull){
        EnumSet<DataType> set = EnumSet.noneOf(DataType.class);
        if (!(ignoreNull && user.getName()==null) && !userDataChecker.check(NAME, user.getName())) set.add(NAME);
        if (!(ignoreNull && user.getSurname()==null) && !userDataChecker.check(SURNAME, user.getSurname())) set.add(SURNAME);
        if (!(ignoreNull && user.getEmail()==null) && !userDataChecker.check(EMAIL, user.getEmail())) set.add(EMAIL);
        if (!(ignoreNull && user.getUsername()==null) && !userDataChecker.check(NICKNAME, user.getUsername())) set.add(NICKNAME);
        if (!(ignoreNull && user.getPassword()==null) && !userDataChecker.check(PASSWORD, user.getPassword())) set.add(PASSWORD);
        return set;
    }
}
