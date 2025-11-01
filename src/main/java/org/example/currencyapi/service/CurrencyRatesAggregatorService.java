package org.example.currencyapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.currencyapi.model.dto.internal.CurrencyRatesDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyRatesAggregatorService {
    private final CurrencyRateService currencyRateService;

    public Mono<CurrencyRatesDto> getAllCurrencyRates() {
        return null;
    }
}
