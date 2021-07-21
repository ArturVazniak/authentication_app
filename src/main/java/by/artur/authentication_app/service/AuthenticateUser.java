package by.artur.authentication_app.service;

import org.springframework.http.ResponseEntity;

public interface AuthenticateUser {
    ResponseEntity<?> authenticateUser(String username, String password);
}
