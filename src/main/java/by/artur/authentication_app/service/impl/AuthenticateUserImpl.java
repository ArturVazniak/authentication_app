package by.artur.authentication_app.service.impl;

import by.artur.authentication_app.model.RefreshToken;
import by.artur.authentication_app.payload.response.JwtResponse;
import by.artur.authentication_app.security.jwt.JwtUtils;
import by.artur.authentication_app.security.jwt.services.RefreshTokenService;
import by.artur.authentication_app.security.jwt.services.UserDetailsImpl;
import by.artur.authentication_app.service.AuthenticateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserImpl implements AuthenticateUser {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthenticateUserImpl(AuthenticationManager authenticationManager,
                                JwtUtils jwtUtils,
                                RefreshTokenService refreshTokenService) {

        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public ResponseEntity<?> authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken()));
    }
}
