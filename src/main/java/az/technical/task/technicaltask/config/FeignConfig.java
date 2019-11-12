package az.technical.task.technicaltask.config;

import az.technical.task.technicaltask.client.MsAuthenticationClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {MsAuthenticationClient.class})
public class FeignConfig {
}