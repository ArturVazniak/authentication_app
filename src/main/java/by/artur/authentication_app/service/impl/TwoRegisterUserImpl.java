package by.artur.authentication_app.service.impl;

import by.artur.authentication_app.model.AccountCode;
import by.artur.authentication_app.model.User;
import by.artur.authentication_app.payload.request.LoginRequest;
import by.artur.authentication_app.payload.request.TwoFactorRequest;
import by.artur.authentication_app.payload.response.MessageResponse;
import by.artur.authentication_app.payload.response.TwoFactorResponse;
import by.artur.authentication_app.repository.AccountCodeRepository;
import by.artur.authentication_app.repository.UserRepository;
import by.artur.authentication_app.security.twoFA.TwoFactorAuth;
import by.artur.authentication_app.service.AuthenticateUser;
import by.artur.authentication_app.service.TwoRegisterUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TwoRegisterUserImpl implements TwoRegisterUser {

    private final UserRepository userRepository;
    private final TwoFactorAuth twoFactorAuth;
    private final AccountCodeRepository accountCodeRepository;
    private final AuthenticateUser authenticateUser;

    @Override
    public ResponseEntity<?> twoRegisterUser(LoginRequest loginRequest) {
        if(userRepository.existsByUsername(loginRequest.getUsername())){
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            String secret = twoFactorAuth.generateSecretKey();
            User user = userRepository.findByUsername(loginRequest.getUsername()).get();
            String twoFactorToken = UUID.randomUUID().toString();

            AccountCode accountCode = AccountCode.builder()
                    .code(secret)
                    .twoFactorToken(twoFactorToken)
                    .user(user)
                    .dateAt(now).build();
            accountCodeRepository.save(accountCode);

            return ResponseEntity.ok(new TwoFactorResponse(twoFactorAuth.generateQRUrl(user,secret), twoFactorToken, secret));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Error: user is not exist"));
    }

    @Override
    public ResponseEntity<?> rideQrCode(TwoFactorRequest twoFactorRequest, String secretKey) {

        if(!twoFactorRequest.getTwoFactorToken().isEmpty()) {

            AccountCode accountCode = accountCodeRepository.findByTwoFactorToken(twoFactorRequest.getTwoFactorToken());
            log.info("IN rideQrCode accountCode with id : {}", accountCode.getId());

            if (twoFactorRequest.getTwoFactorToken().equals(accountCode.getTwoFactorToken())) {
                Totp totp = new Totp(twoFactorRequest.getSecretKey());
                if (!isValidLong(secretKey) || !totp.verify(secretKey)) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Invalid verification code"));
                } else {
                    accountCodeRepository.delete(accountCode);

                    return authenticateUser.authenticateUser(twoFactorRequest.getUsername(), twoFactorRequest.getPassword());
                }
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Two factor token not exist"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Two factor token not valid"));
    }

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

}
