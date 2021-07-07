package by.artur.authentication_app.security.jwt;

import by.artur.authentication_app.model.Role;
import by.artur.authentication_app.model.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class JwtUserFactory {

    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.isEnabled(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRole){
        return userRole.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
