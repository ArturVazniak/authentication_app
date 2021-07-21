package by.artur.authentication_app.service;

import by.artur.authentication_app.payload.request.TokenRefreshRequest;
import org.springframework.http.ResponseEntity;

public interface RefreshTokenController {
    ResponseEntity<?> refreshToken(TokenRefreshRequest request);
}
