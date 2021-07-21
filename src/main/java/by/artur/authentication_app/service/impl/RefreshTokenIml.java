package by.artur.authentication_app.service.impl;

import by.artur.authentication_app.exception.TokenRefreshException;
import by.artur.authentication_app.payload.request.TokenRefreshRequest;
import by.artur.authentication_app.payload.response.TokenRefreshResponse;
import by.artur.authentication_app.security.jwt.JwtUtils;
import by.artur.authentication_app.security.jwt.services.RefreshTokenService;
import by.artur.authentication_app.service.RefreshTokenController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenIml implements RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    private final JwtUtils jwtUtils;

    @Autowired
    public RefreshTokenIml(RefreshTokenService refreshTokenService, JwtUtils jwtUtils) {
        this.refreshTokenService = refreshTokenService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(by.artur.authentication_app.model.RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsernameAndRole(user.getUsername(),user.getRoles().toString());//<----
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
}
