package az.technical.task.technicaltask.filter;

import az.technical.task.technicaltask.client.MsAuthenticationClient;
import az.technical.task.technicaltask.model.client.auth.UserInfo;
import az.technical.task.technicaltask.security.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static az.technical.task.technicaltask.model.client.auth.HttpHeader.X_AUTH_TOKEN;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private MsAuthenticationClient authenticationClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authToken = request.getHeader(X_AUTH_TOKEN);
            if (authToken != null) {
                //Boolean validToken = authenticationClient.validateToken(authToken);
                UserInfo userInfo = authenticationClient.validateToken(authToken);
                System.out.println(userInfo.getCustomerId() + " " + userInfo.getEmail());
                if (userInfo == null) {
                    throw new RuntimeException("User info is not valid");
                } else {
                    UserAuthentication userAuthentication = new UserAuthentication(userInfo.getCustomerId(),
                            az.technical.task.technicaltask.security.UserInfo
                                    .builder()
                                    .customerId(userInfo.getCustomerId())
                                    .email(userInfo.getEmail())
                                    .build(),
                            true);
                    SecurityContextHolder.getContext().setAuthentication(userAuthentication);
                    System.out.println("User info is valid");
                }
            }
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    @Autowired
    public void setAuthenticationClient(MsAuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

}
