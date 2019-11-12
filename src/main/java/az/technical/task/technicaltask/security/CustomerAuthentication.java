package az.technical.task.technicaltask.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomerAuthentication implements Authentication {

    private String customerId;
    private CustomerInfo customerInfo;
    private boolean authenticated;
    private String role;

    public CustomerAuthentication(String customerId, CustomerInfo customerInfo, boolean authenticated, String role) {
        this.customerId = customerId;
        this.customerInfo = customerInfo;
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
    public CustomerInfo getDetails() {
        return customerInfo;
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
}
