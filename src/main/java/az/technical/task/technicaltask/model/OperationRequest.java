package az.technical.task.technicaltask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationRequest {

    private OperationFilter filter;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OperationFilter {
        private String customerId;
        private BigDecimal minAmount;
        private BigDecimal maxAmount;
        private String currency;
        private String category;
        private String description;
        private int date;
    }
}

