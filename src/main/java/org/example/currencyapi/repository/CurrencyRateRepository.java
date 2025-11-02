package org.example.currencyapi.repository;

import org.example.currencyapi.model.CurrencyRateEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CurrencyRateRepository extends ReactiveCrudRepository<CurrencyRateEntity, Long> {
    Flux<CurrencyRateEntity> findByCurrencyType(String currencyType);
}
