package az.technical.task.technicaltask.client;

import az.technical.task.technicaltask.model.client.auth.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static az.technical.task.technicaltask.model.client.auth.HttpHeader.X_AUTH_TOKEN;

@Component
@FeignClient(name = "ms-auth", url = "http://localhost:8282")
public interface MsAuthenticationClient {
    @PostMapping("/auth/validate")
    UserInfo validateToken(@RequestHeader(X_AUTH_TOKEN) String token);

    @GetMapping("customer/id/by/email/{email}")
    String getCustomerIdByEmail( @RequestHeader(X_AUTH_TOKEN)String token,
                                 @PathVariable(name = "email") String email);
}