package org.example.currencyapi.model.dto.external;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class FiatApiResponse {

    private String currency;
    private BigDecimal rate;
}
