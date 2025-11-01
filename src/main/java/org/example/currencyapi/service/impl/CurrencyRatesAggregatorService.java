package org.example.currencyapi.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.example.currencyapi.model.dto.internal.CurrencyRateDto;
import org.example.currencyapi.model.dto.internal.CurrencyRatesDto;
import org.example.currencyapi.model.enums.CurrencyType;
import org.example.currencyapi.service.CurrencyRateService;
import org.example.currencyapi.service.client.CurrencyRatesClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CurrencyRatesAggregatorService {
    private final CurrencyRateService currencyRateService;
    private final Map<CurrencyType, CurrencyRatesClient> currencyRatesClientMap;

    public CurrencyRatesAggregatorService(CurrencyRateService currencyRateService, List<CurrencyRatesClient> clients) {
        this.currencyRateService = currencyRateService;
        this.currencyRatesClientMap = clients.stream()
                .collect(Collectors.toMap(
                        CurrencyRatesClient::getCurrencyType, client -> client));
    }

    public Mono<CurrencyRatesDto> getAllCurrencyRates() {
        Mono<List<CurrencyRateDto>> fiatRates = fetchRatesForType(CurrencyType.FIAT);
        Mono<List<CurrencyRateDto>> cryptoRates = fetchRatesForType(CurrencyType.CRYPTO);

        return Mono.zip(fiatRates, cryptoRates)
                .map(tuple -> {
                    List<CurrencyRateDto> fiat = tuple.getT1();
                    List<CurrencyRateDto> crypto = tuple.getT2();

                    return CurrencyRatesDto.builder()
                            .fiat(fiat)
                            .crypto(crypto)
                            .build();
                })
                .doOnError(ex ->
                        log.warn("Error in aggregation: {}", ex.getMessage()));

    }

    private Mono<List<CurrencyRateDto>> fetchRatesForType(CurrencyType currencyType) {
        CurrencyRatesClient client = currencyRatesClientMap.get(currencyType);

        if (client == null) {
            log.warn("Client not found for type {}", currencyType);
            return Mono.just(List.of());
        } else {
            return currencyRateService.fetchAndSaveRates(client.fetchRates(), currencyType);
        }
    }
}
