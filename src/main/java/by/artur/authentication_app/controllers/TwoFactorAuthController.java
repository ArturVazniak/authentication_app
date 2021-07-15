package by.artur.authentication_app.controllers;

import by.artur.authentication_app.model.AccountCode;
import by.artur.authentication_app.model.User;
import by.artur.authentication_app.payload.request.LoginRequest;
import by.artur.authentication_app.payload.response.MessageResponse;
import by.artur.authentication_app.payload.response.TwoFactorResponse;
import by.artur.authentication_app.repository.AccountCodeRepository;
import by.artur.authentication_app.repository.UserRepository;
import by.artur.authentication_app.security.twoFA.TwoFactorAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.jboss.aerogear.security.otp.Totp;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


@RestController
public class TwoFactorAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TwoFactorAuth twoFactorAuth;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private AccountCodeRepository accountCodeRepository;

    @PostMapping("two-registration")
    public ResponseEntity<?> twoRegisterUser(@Valid @RequestBody LoginRequest loginRequest){
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

            return ResponseEntity.ok(new TwoFactorResponse(twoFactorAuth.generateQRUrl(user,secret), twoFactorToken));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Error: user is not exist"));
    }

    @PostMapping("ride-code")
   public ResponseEntity<?> rideQrCode(@RequestBody TwoFactorResponse twoFactorResponse, String secretKey){

        AccountCode accountCode = accountCodeRepository.findByTwoFactorToken(twoFactorResponse.getTwoFactorToken());

        if(twoFactorResponse.getTwoFactorToken().equals(accountCode.getTwoFactorToken())) {
            Totp totp = new Totp(accountCode.getCode());
            if (!isValidLong(secretKey) || !totp.verify(secretKey)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Invalid verification code"));
            } else {
                accountCodeRepository.deleteByCode(secretKey);
                return ResponseEntity.ok("Hello");
            }
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
