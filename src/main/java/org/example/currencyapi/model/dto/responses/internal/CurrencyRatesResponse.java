package org.example.currencyapi.model.dto.responses.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class CurrencyRatesResponse {

    @JsonProperty("fiat")
    private List<CurrencyRateResponse> fiat;

    @JsonProperty("crypto")
    private List<CurrencyRateResponse> crypto;
}
