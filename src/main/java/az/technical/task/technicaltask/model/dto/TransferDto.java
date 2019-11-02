package az.technical.task.technicaltask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    private String customerId;
    private String toppedUpAccountId;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private String category;
    private String description;
}
