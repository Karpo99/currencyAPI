package org.example.currencyapi.service;

import java.util.List;
import org.example.currencyapi.model.dto.internal.CurrencyRateDto;
import org.example.currencyapi.model.enums.CurrencyType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrencyRateService {
    Mono<List<CurrencyRateDto>> fetchAndSaveRates(Flux<CurrencyRateDto> apiRates,
                                                  CurrencyType currencyType);
}
