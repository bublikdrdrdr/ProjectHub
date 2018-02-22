package app.service.implementation;

import app.exception.*;
import app.repository.dao.BlockRepository;
import app.repository.dao.BookmarkRepository;
import app.repository.dao.ImageRepository;
import app.repository.dao.UserRepository;
import app.repository.dto.*;
import app.repository.entity.User;
import app.repository.entity.UserBlock;
import app.repository.entity.UserBookmark;
import app.repository.etc.BookmarkSearchParams;
import app.repository.etc.SearchParams;
import app.repository.etc.UserSearchParams;
import app.service.AuthenticationService;
import app.service.DataType;
import app.service.UserDataChecker;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import app.exception.UserAlreadyExistsException.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.security.auth.login.FailedLoginException;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static app.service.DataType.*;
import static app.util.ServiceUtils.now;

@Service
@PropertySource("service.properties")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserDataChecker userDataChecker;
    private PasswordEncoder passwordEncoder;
    private BookmarkRepository bookmarkRepository;
    private AuthenticationService authenticationService;
    private ImageRepository imageRepository;
    private BlockRepository blockRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDataChecker userDataChecker,
                           PasswordEncoder passwordEncoder, BookmarkRepository bookmarkRepository,
                           AuthenticationService authenticationService, ImageRepository imageRepository,
                           BlockRepository blockRepository) {
        this.userRepository = userRepository;
        this.userDataChecker = userDataChecker;
        this.passwordEncoder = passwordEncoder;
        this.bookmarkRepository = bookmarkRepository;
        this.authenticationService = authenticationService;
        this.imageRepository = imageRepository;
        this.blockRepository = blockRepository;
    }

    @Override
    public UserDTO get(long id) {
        User user = userRepository.get(id, EnumSet.of(UserRepository.Include.BLOCKS, UserRepository.Include.IMAGES));
        if (user == null) throw new EntityNotFoundException("User with id " + id + " doesn't exists");
        return new UserDTO(user, user.getImage() == null ? null : user.getImage().getId(), user.isBlocked(now()));
    }

    @Override
    public long register(UserRegistrationDTO userData) throws UserAlreadyExistsException, InvalidFieldFormatException {

        User user = userData.convertToUser(now(), null);
        EnumSet<DataType> dataChecks = checkUserData(user);
        if (dataChecks.size() > 0) throw new InvalidFieldFormatException(dataChecks);
        EnumSet<ConflictField> conflicts = checkConflicts(user);
        if (conflicts.size() > 0) throw new UserAlreadyExistsException(conflicts);
        return userRepository.save(user);
    }

    private boolean checkFieldAvailable(DataType dataType, String value) {
        return checkFieldAvailable(new FieldCheckDTO(dataType, value));
    }

    @Override
    public boolean checkFieldAvailable(FieldCheckDTO field) throws FieldAvailabilityCheckException {
        try {
            switch (field.userField) {
                case EMAIL:
                    return (userRepository.getByEmail(field.value) == null);
                case NICKNAME:
                    return (userRepository.getByUsername(field.value) == null);
                default:
                    throw new InvalidTypeException(field.userField);
            }
        } catch (Exception e) {
            throw new FieldAvailabilityCheckException(field.userField, e);
        }
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) throws AuthenticationException {
        try {
            User user = userRepository.getByEmail(request.email);
            if (user == null) throw new AuthenticationException("User not found");
            if (passwordEncoder.matches(request.password, user.getPassword()))
                return new LoginResponseDTO(user.getId(), "token");
            else throw new AuthenticationException("Passwords don't match");//TODO: generate token
        } catch (AuthenticationException | NullPointerException e) {
            throw new AuthenticationException("Cannot authenticate user", e);
        }
    }

    @Value("${permission.can_update_email:false}")
    private boolean canUpdateEmail;

    @Value("${permission.can_update_username:false}")
    private boolean canUpdateUsername;

    @Override
    public void update(UserRegistrationDTO user) throws InvalidFieldFormatException {
        User dbUser = userRepository.get(user.id);
        EnumSet<DataType> errors = checkUserData(user.convertToUser(null, null), true);
        if (errors.size() > 0) throw new InvalidFieldFormatException(errors);
        if (!canUpdateEmail && user.email != null && !user.email.equals(dbUser.getEmail()))
            throw new SetValueException("Update email not allowed");
        if (!canUpdateUsername && user.username != null && !user.username.equals(dbUser.getUsername()))
            throw new SetValueException("Update username not allowed");
        if (canUpdateEmail && user.email != null) dbUser.setEmail(user.email);
        if (canUpdateUsername && user.username != null) dbUser.setUsername(user.username);
        if (user.name != null) dbUser.setName(user.name);
        if (user.surname != null) dbUser.setSurname(user.surname);
        if (user.password != null) dbUser.setPassword(user.password);
        userRepository.save(dbUser);
    }

    @Override
    public UserSearchResponseDTO search(UserSearchRequestDTO request) {
        UserSearchParams usp = request.getSearchParams();
        List<User> users = userRepository.search(usp);
        long count = userRepository.count(usp);
        return new UserSearchResponseDTO(count, userListToDTO(users));
    }

    @Override
    public void bookmark(long id) throws EntityAlreadyExistsException {
        User user = authenticationService.getAuthenticatedUser();
        User bookmarkedUser = userRepository.get(id);
        if (bookmarkedUser == null) throw new EntityNotFoundException("Bookmarked user with id " + id + " not found");
        UserBookmark bookmark = bookmarkRepository.getByRelation(user, bookmarkedUser);
        if (bookmark != null) throw new EntityAlreadyExistsException("User is bookmarked");
        bookmark = new UserBookmark(null, user, bookmarkedUser, now());
        bookmarkRepository.save(bookmark);
    }

    @Override
    public BookmarkSearchResponseDTO getBookmarks(BookmarkSearchRequestDTO request) {
        User user = authenticationService.getAuthenticatedUser();
        BookmarkSearchParams searchParams = request.getSearchParams(user);
        List<UserBookmark> list = bookmarkRepository.search(searchParams);
        long count = bookmarkRepository.count(searchParams);
        BookmarkSearchResponseDTO response = new BookmarkSearchResponseDTO();
        response.count = count;
        response.items = list.stream().map(userBookmark -> {
            User bookmarked = userBookmark.getBookmarked();
            return new BookmarkDTO(new UserDTO(bookmarked,
                    imageRepository.getUserImage(bookmarked).getId(),
                    blockRepository.isBlocked(bookmarked)),
                    userBookmark.getAdded().getTime());
        }).collect(Collectors.toList());
        return response;
    }

    @Override
    public void removeBookmark(long id) {
        User user = authenticationService.getAuthenticatedUser();
        User bookmarked = userRepository.get(id);
        UserBookmark bookmark = bookmarkRepository.getByRelation(user, bookmarked);
        bookmarkRepository.remove(bookmark);
    }

    @Override
    public void updateOnline() {
        User user = authenticationService.getAuthenticatedUser();
        user = userRepository.get(user.getId());
        user.setLastOnline(now());
        userRepository.save(user);
    }

    private EnumSet<ConflictField> checkConflicts(User user) {
        EnumSet<ConflictField> conflicts = EnumSet.noneOf(ConflictField.class);
        if (checkFieldAvailable(DataType.NICKNAME, user.getUsername())) conflicts.add(ConflictField.USERNAME);
        if (checkFieldAvailable(DataType.EMAIL, user.getEmail())) conflicts.add(ConflictField.EMAIL);
        return conflicts;
    }

    private EnumSet<DataType> checkUserData(User user) {
        return checkUserData(user, false);
    }

    private EnumSet<DataType> checkUserData(User user, boolean ignoreNull) {
        EnumSet<DataType> set = EnumSet.noneOf(DataType.class);
        if (!(ignoreNull && user.getName() == null) && !userDataChecker.check(NAME, user.getName())) set.add(NAME);
        if (!(ignoreNull && user.getSurname() == null) && !userDataChecker.check(SURNAME, user.getSurname()))
            set.add(SURNAME);
        if (!(ignoreNull && user.getEmail() == null) && !userDataChecker.check(EMAIL, user.getEmail())) set.add(EMAIL);
        if (!(ignoreNull && user.getUsername() == null) && !userDataChecker.check(NICKNAME, user.getUsername()))
            set.add(NICKNAME);
        if (!(ignoreNull && user.getPassword() == null) && !userDataChecker.check(PASSWORD, user.getPassword()))
            set.add(PASSWORD);
        return set;
    }

    private List<UserDTO> userListToDTO(List<User> users) {
        return users.stream().map(user ->
                new UserDTO(user, (user.getImage() == null ? null : user.getImage().getId()),
                        null)).collect(Collectors.toList());
    }

    private boolean checkNothingNull(Object[] objects) {
        return Arrays.stream(objects).noneMatch(Objects::isNull);
    }
}