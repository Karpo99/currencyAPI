package org.example.currencyapi.model.dto.responses.external;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;

@Data
public class CryptoApiResponse {

    private Map<String, BigDecimal> rates;
}
