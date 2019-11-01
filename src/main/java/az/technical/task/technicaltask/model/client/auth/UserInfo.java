package az.technical.task.technicaltask.model.client.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    // private Long userId;
    private String customerId;
    private String email;
}
