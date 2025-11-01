package org.example.currencyapi.model.dto.internal;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyRateDto {
    private String currency;
    private BigDecimal rate;
}
