package org.example.currencyapi.model.dto.external;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CryptoApiResponse {

    private String name;
    private BigDecimal value;
}
