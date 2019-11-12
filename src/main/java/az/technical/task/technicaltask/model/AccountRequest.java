package az.technical.task.technicaltask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    private AccountFilter filter;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountFilter {
        private String accountId;
        private String currency;
        private BigDecimal minAmount;
        private BigDecimal maxAmount;
    }
}

