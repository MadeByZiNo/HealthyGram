package madebyzino.HealthyGram.infra.security.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import madebyzino.HealthyGram.domain.user.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Getter

public class CustomUserDetails implements UserDetails {

    private String id;
    private String email;
    private UserRole role;
    private boolean isMetaRegistered;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return id;
    }
}
