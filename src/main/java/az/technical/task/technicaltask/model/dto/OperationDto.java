package az.technical.task.technicaltask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationDto {
    private String accountId;
    private LocalDateTime date;
    private Boolean increased;
    private BigDecimal amount;
    private String currency;
    private String category;
    private String description;


}
