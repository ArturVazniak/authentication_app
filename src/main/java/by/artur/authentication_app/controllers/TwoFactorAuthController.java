package by.artur.authentication_app.controllers;

import by.artur.authentication_app.model.User;
import by.artur.authentication_app.payload.request.LoginRequest;
import by.artur.authentication_app.payload.response.MessageResponse;
import by.artur.authentication_app.payload.response.TwoFactorResponse;
import by.artur.authentication_app.repository.UserRepository;
import by.artur.authentication_app.security.twoFA.TwoFactorAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class TwoFactorAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TwoFactorAuth twoFactorAuth;

    @Autowired
    PasswordEncoder encoder;


    @PostMapping("two-registration")
    public ResponseEntity<?> twoRegisterUser(@Valid @RequestBody LoginRequest loginRequest){
        if(userRepository.existsByUsername(loginRequest.getUsername())){
            String secret = twoFactorAuth.generateSecretKey();
            String encoderSecret = encoder.encode(secret);

            User user = userRepository.findByUsername(loginRequest.getUsername()).get();
            return ResponseEntity.ok(new TwoFactorResponse(encoderSecret, twoFactorAuth.generateQRUrl(user,secret)));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Error: user is not exist"));
    }

    @PostMapping("ride-code")
   public ResponseEntity<?> rideQrCode(@RequestBody TwoFactorResponse twoFactorResponse,String secretKey){
        String code = twoFactorAuth.getTOTPCode(secretKey);
        System.out.println(encoder.encode(code));
        System.out.println(twoFactorResponse.getSecret());
        if(encoder.encode(code).equals(twoFactorResponse.getSecret())){
            return ResponseEntity.ok("Hello");
        }

        return ResponseEntity.ok("Ho Hello");
    }

}
