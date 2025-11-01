package org.example.currencyapi.service.client.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.currencyapi.model.dto.external.CryptoApiResponse;
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
public class CryptoRatesClient implements CurrencyRatesClient {
    private final WebClient webClient;

    @Value("${mock.crypto-api.endpoint}")
    private String cryptoApiEndpoint;

    @Override
    @Retry(name = "cryptoRatesClient")
    @CircuitBreaker(name = "cryptoRatesClient", fallbackMethod = "fallbackFetchRatesFromDb")
    public Flux<CurrencyRateDto> fetchRates() {
        return webClient.get()
                .uri(cryptoApiEndpoint)
                .retrieve()
                .bodyToFlux(CryptoApiResponse.class)  // because response = JSON array
                .doOnSubscribe(sub -> log.info("Fetching CRYPTO rates..."))
                .doOnError(ex -> log.warn("Error receiving CRYPTO rates: {}", ex.getMessage()))
                .doOnComplete(() -> log.info("Successfully received CRYPTO rates"))
                .map(dto -> CurrencyRateDto.builder()
                        .currency(dto.getName())
                        .rate(dto.getValue())
                        .build());


    }

    @Override
    public CurrencyType getCurrencyType() {
        return CurrencyType.CRYPTO;
    }

    private Flux<CurrencyRateDto> fallbackFetchRatesFromDb(Throwable throwable) {
        log.warn("Crypto API fallback triggered {}", throwable.getMessage());
        log.info("Returning empty Flux, proceed db fallback from service layer");
        return Flux.empty();
    }
}
