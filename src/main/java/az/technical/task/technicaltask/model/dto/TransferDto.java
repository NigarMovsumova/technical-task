package az.technical.task.technicaltask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    private String customerId;
    private String accountId;
    private LocalDateTime date;
    private Boolean increased;
    private BigDecimal amount;
    private String currency;
    private String category;
    private String description;
}
