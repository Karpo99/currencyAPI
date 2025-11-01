package org.example.currencyapi.model.dto.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class CurrencyRatesDto {

    @JsonProperty("fiat")
    private List<CurrencyRateDto> fiat;

    @JsonProperty("crypto")
    private List<CurrencyRateDto> crypto;
}
