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
    private String senderAccountId;
    private String receiverAccountId;
    private BigDecimal amount;
    private boolean increased;
    private String currency;
    private String category;
    private String description;
}
