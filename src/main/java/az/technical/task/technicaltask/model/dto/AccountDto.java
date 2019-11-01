package az.technical.task.technicaltask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String customerId;
    private BigDecimal amount;
    private String currency;
    private String accountId;
    private String status;
}
