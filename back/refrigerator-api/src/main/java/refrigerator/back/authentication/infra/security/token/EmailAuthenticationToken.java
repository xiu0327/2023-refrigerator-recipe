package refrigerator.back.authentication.infra.security.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class EmailAuthenticationToken implements Authentication {

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public EmailAuthenticationToken(String username, String password,
                                    Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        setAuthenticated(true);
    }

    public EmailAuthenticationToken(String username, String password) {
        this.username = username;
        this.password = password;
        this.authorities = null;
        setAuthenticated(false);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return username;
    }
}