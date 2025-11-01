package org.example.currencyapi.repository;

import org.example.currencyapi.model.CurrencyRateEntity;
import org.example.currencyapi.model.dto.internal.CurrencyRateDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CurrencyRateRepository extends ReactiveCrudRepository<CurrencyRateEntity, Long> {
    Flux<CurrencyRateDto> findByCurrencyType(String currencyType);
}
