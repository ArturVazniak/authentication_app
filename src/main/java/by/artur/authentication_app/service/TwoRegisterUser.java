package by.artur.authentication_app.service;

import by.artur.authentication_app.payload.request.LoginRequest;
import by.artur.authentication_app.payload.request.TwoFactorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TwoRegisterUser {
    ResponseEntity<?> twoRegisterUser(LoginRequest loginRequest);

    ResponseEntity<?> rideQrCode(TwoFactorRequest twoFactorRequest, String secretKey);
}
