package by.artur.authentication_app.controllers;

import by.artur.authentication_app.payload.request.*;
import by.artur.authentication_app.payload.response.MessageResponse;
import by.artur.authentication_app.security.jwt.services.RefreshTokenService;
import by.artur.authentication_app.service.AuthenticateUser;
import by.artur.authentication_app.service.RefreshTokenController;
import by.artur.authentication_app.service.RegisterUser;
import by.artur.authentication_app.service.TwoRegisterUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RefreshTokenService refreshTokenService;
    private final AuthenticateUser authenticateUser;
    private final RegisterUser registerUser;
    private final RefreshTokenController refreshTokenController;
    private final TwoRegisterUser twoRegisterUser;

    @PostMapping("/register")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        if(loginRequest.isMfa()) {
            return twoRegisterUser.twoRegisterUser(loginRequest);
        }

        return authenticateUser.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return registerUser.registerUser(signUpRequest);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return refreshTokenController.refreshToken(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

    @PostMapping("ride-code")
    public ResponseEntity<?> rideQrCode(@RequestBody TwoFactorRequest twoFactorRequest, String secret){
        return twoRegisterUser.rideQrCode(twoFactorRequest, secret);
    }


}
