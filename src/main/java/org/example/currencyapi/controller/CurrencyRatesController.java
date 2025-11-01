package org.example.currencyapi.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.currencyapi.model.dto.internal.CurrencyRatesDto;
import org.example.currencyapi.service.impl.CurrencyRatesAggregatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
public class CurrencyRatesController {

    private final CurrencyRatesAggregatorService aggregatorService;

    @GetMapping("/currency-rates")
    public Mono<CurrencyRatesDto> getCurrencyRates() {
        log.info("Received GET request to /currency-rates");

        return aggregatorService.getAllCurrencyRates();
    }
}
