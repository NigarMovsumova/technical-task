package az.technical.task.technicaltask.config;

import az.technical.task.technicaltask.client.MsAuthenticationClient;
import az.technical.task.technicaltask.client.MsPaymentClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {
        MsAuthenticationClient.class, MsPaymentClient.class})
public class FeignConfig {
}