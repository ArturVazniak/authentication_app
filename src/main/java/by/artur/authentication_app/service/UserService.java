package by.artur.authentication_app.service;

import by.artur.authentication_app.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void findByFingerprint(String fingerprint, User user);


}
