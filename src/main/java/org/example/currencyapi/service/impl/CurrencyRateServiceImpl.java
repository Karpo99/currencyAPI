package org.example.currencyapi.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.currencyapi.model.CurrencyRateEntity;
import org.example.currencyapi.model.dto.internal.CurrencyRateDto;
import org.example.currencyapi.model.enums.CurrencyType;
import org.example.currencyapi.repository.CurrencyRateRepository;
import org.example.currencyapi.service.CurrencyRateService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyRateServiceImpl implements CurrencyRateService {
    private final CurrencyRateRepository currencyRateRepository;

    @Override
    public Mono<List<CurrencyRateDto>> fetchAndSaveRates(Flux<CurrencyRateDto> apiRates,
                                                         CurrencyType currencyType) {
        return apiRates.collectList()
                .flatMap(ratesList -> {

                    if (!ratesList.isEmpty()) {
                        log.info("API returned {} {} rates, saving to DB", ratesList.size(), currencyType);

                        return saveRatesToDatabase(ratesList, currencyType)
                                .thenReturn(ratesList);
                    } else {
                        log.warn("API returned empty {} rates, attempting fallback to DB", currencyType);

                        return fetchFromDatabase(currencyType);
                    }
                })
                .onErrorResume(ex -> {
                    log.warn("Unexpected fail during processing {} rates, attempting fallback to DB", currencyType, ex);

                    return fetchFromDatabase(currencyType);
                });
    }

    private Mono<List<CurrencyRateDto>> fetchFromDatabase(CurrencyType currencyType) {
        log.info("Fetching {} rates from DB as fallback", currencyType);

        return currencyRateRepository.findByCurrencyType(currencyType.name())
                .map(entity -> CurrencyRateDto.builder()
                        .currency(entity.getCurrency())
                        .rate(entity.getRate())
                        .build())
                .collectList()
                .doOnNext(list -> {
                    if (list.isEmpty()) {
                        log.warn("DB is empty for {}, returning an empty array", currencyType);
                    } else {
                        log.info("Fetched {} {} rates from DB", currencyType, list.size());
                    }
                })
                .onErrorResume(error -> {
                    log.error("Database error while reading {} {} rates", currencyType, error);

                    return Mono.just(List.of());
                });
    }

    private Mono<Void> saveRatesToDatabase(List<CurrencyRateDto> ratesList, CurrencyType currencyType) {
        log.info("Saving {} rates to DB", currencyType);

        return Flux.fromIterable(ratesList)
                .map(dto -> CurrencyRateEntity.builder()
                        .currency(dto.getCurrency())
                        .rate(dto.getRate())
                        .currencyType(currencyType.name())
                        .createdAt(LocalDateTime.now())
                        .build())
                .as(currencyRateRepository::saveAll)
                .doOnNext(saved ->
                        log.debug("Saved {} rate {}={}", currencyType, saved.getCurrency(), saved.getRate()))
                .then();
    }
}
