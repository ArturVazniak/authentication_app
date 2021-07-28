package by.artur.authentication_app.security.jwt;

import by.artur.authentication_app.model.Role;
import by.artur.authentication_app.model.User;
import by.artur.authentication_app.repository.RoleRepository;
import by.artur.authentication_app.repository.UserRepository;
import by.artur.authentication_app.security.jwt.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.token.secret}")
    private String jwtSecret;

    @Value("${jwt.token.expired}")
    private int jwtExpirationMs;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public JwtUtils(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String generateJwtToken(UserDetailsImpl userPrincipal) {
        User user = userRepository.findByUsername(userPrincipal.getUsername()).get();
        Role role = roleRepository.findById(user.getId()).get();
        return generateTokenFromUsernameAndRole(userPrincipal.getUsername(),role.getName().name());
    }


    public String generateTokenFromUsernameAndRole(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("Role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
