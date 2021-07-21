package by.artur.authentication_app.service;

import by.artur.authentication_app.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;


public interface RegisterUser {
     ResponseEntity<?> registerUser(SignupRequest signUpRequest);

}
