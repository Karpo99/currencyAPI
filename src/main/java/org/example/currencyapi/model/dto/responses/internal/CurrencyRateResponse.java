package org.example.currencyapi.model.dto.responses.internal;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CurrencyRateResponse {
    private String currency;
    private BigDecimal rate;
}
