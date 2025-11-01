package org.example.currencyapi.service.client;

import org.example.currencyapi.model.dto.internal.CurrencyRateDto;
import org.example.currencyapi.model.enums.CurrencyType;
import reactor.core.publisher.Flux;

public interface CurrencyRatesClient {
    Flux<CurrencyRateDto> fetchRates();
    CurrencyType getCurrencyType();
}
