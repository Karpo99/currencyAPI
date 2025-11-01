package org.example.currencyapi.model.dto.external;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;

@Data
public class FiatApiDto {

    private Map<String, BigDecimal> rates;
}
