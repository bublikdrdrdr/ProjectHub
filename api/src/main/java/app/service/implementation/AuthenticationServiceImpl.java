package app.service.implementation;

import app.repository.dao.UserRepository;
import app.repository.entity.User;
import app.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getAuthenticatedUser() {
        return userRepository.get(1); //TODO: only for testing
    }
}
