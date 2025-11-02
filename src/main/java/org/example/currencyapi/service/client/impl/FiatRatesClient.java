package org.example.currencyapi.service.client.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.currencyapi.model.dto.external.FiatApiResponse;
import org.example.currencyapi.model.dto.internal.CurrencyRateDto;
import org.example.currencyapi.model.enums.CurrencyType;
import org.example.currencyapi.service.client.CurrencyRatesClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class FiatRatesClient implements CurrencyRatesClient {
    private final WebClient webClient;

    @Value("${mock.fiat-api.endpoint}")
    private String fiatApiEndpoint;

    @Override
    @Retry(name = "fiatRatesClient")
    @CircuitBreaker(name = "fiatRatesClient", fallbackMethod = "fallbackFetchRatesFromDb")
    public Flux<CurrencyRateDto> fetchRates() {
        return webClient.get()
                .uri(fiatApiEndpoint)
                .retrieve()
                .bodyToFlux(FiatApiResponse.class)  // because response = JSON array
                .doOnSubscribe(sub -> log.info("Fetching FIAT rates..."))
                .doOnError(ex -> log.warn("Failed to call FIAT endpoint: {}", fiatApiEndpoint, ex))
                .doOnComplete(() -> log.info("Successfully received FIAT rates"))
                .map(dto -> CurrencyRateDto.builder()
                        .currency(dto.getCurrency())
                        .rate(dto.getRate())
                        .build());
    }

    @Override
    public CurrencyType getCurrencyType() {
        return CurrencyType.FIAT;
    }

    private Flux<CurrencyRateDto> fallbackFetchRatesFromDb(Throwable throwable) {
        log.warn("Fiat API fallback triggered {}", fiatApiEndpoint, throwable);
        log.info("Returning empty Flux, proceed db fallback from service layer");
        return Flux.empty();
    }
}
