package org.example.currencyapi.model.dto.external;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;

@Data
public class CryptoApiDto {

    private Map<String, BigDecimal> rates;
}
