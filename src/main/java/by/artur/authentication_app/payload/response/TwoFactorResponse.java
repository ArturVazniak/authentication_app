package by.artur.authentication_app.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TwoFactorResponse {

    private String secret;
    private String qrCode;
    private String twoFactorToken;
}
