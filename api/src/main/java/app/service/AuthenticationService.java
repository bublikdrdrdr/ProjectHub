package app.service;

import app.repository.entity.User;

public interface AuthenticationService {

    User getAuthenticatedUser();
}
