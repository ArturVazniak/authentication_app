package by.innowise.adminservice.model.securityModel;

import lombok.Data;

@Data
public class JwtUser {

    private String username;
    private String role;
}
