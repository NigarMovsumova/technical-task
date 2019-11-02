package az.technical.task.technicaltask.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication {

    private String customerId;
    private UserInfo userInfo;
    private boolean authenticated;
    private String role;

    public UserAuthentication(String customerId, UserInfo userInfo, boolean authenticated, String role) {
        this.customerId = customerId;
        this.userInfo = userInfo;
        this.authenticated = authenticated;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserInfo getDetails() {
        return userInfo;
    }

    @Override
    public String getPrincipal() {
        return customerId;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }


    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String toString() {
        return "UserAuthentication{" +
                "customerId='" + customerId + '\'' +
                ", userInfo=" + userInfo.toString() +
                ", authenticated=" + authenticated +
                ", role='" + role + '\'' +
                '}';
    }
}
