package az.technical.task.technicaltask.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operations")
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "account_id")
    private String accountId;

    //todo createdat
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @Column(name = "increased")
    private Boolean increased;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;
}
