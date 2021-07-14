package by.artur.authentication_app.security.twoFA;

import by.artur.authentication_app.model.User;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import de.taimos.totp.TOTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Component
public class TwoFactorAuth {

    private static final String QR_PREFIX= "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";

    @Value("${appName}")
    private String appName;

    public  String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    public String generateQRUrl(User user, String secret) {
        return QR_PREFIX + URLEncoder.encode(String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                user.getUsername(), user.getEmail(), secret, appName,
                StandardCharsets.UTF_8));
    }
}
