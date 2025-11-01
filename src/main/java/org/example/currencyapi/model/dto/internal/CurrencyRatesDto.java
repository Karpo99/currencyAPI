package org.example.currencyapi.model.dto.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyRatesDto {

    @JsonProperty("fiat")
    private List<CurrencyRateDto> fiat;

    @JsonProperty("crypto")
    private List<CurrencyRateDto> crypto;
}
