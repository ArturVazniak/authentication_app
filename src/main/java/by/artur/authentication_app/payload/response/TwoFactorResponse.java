package by.artur.authentication_app.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TwoFactorResponse {

    private String qrCode;
    private String twoFactorToken;
}
