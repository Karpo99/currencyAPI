package org.example.currencyapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("currency_rates")
public class CurrencyRateEntity {

    @Id
    private Long id;

    private String currency;
    private BigDecimal rate;
    private String currencyType;
    private LocalDateTime createdAt;
}
