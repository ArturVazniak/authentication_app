package by.artur.authentication_app.payload.request;

import lombok.Data;

@Data
public class TwoFactorRequest {
    private String twoFactorToken;
    private String secretKey;
    private String username;
    private String password;

}
