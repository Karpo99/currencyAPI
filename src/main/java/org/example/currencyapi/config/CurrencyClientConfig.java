package org.example.currencyapi.config;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.currencyapi.model.enums.CurrencyType;
import org.example.currencyapi.service.client.CurrencyRatesClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyClientConfig {

    @Bean
    public Map<CurrencyType, CurrencyRatesClient> buildClientsMap(List<CurrencyRatesClient> clients) {
        return clients.stream()
                .collect(Collectors.toMap(
                        CurrencyRatesClient::getCurrencyType, client -> client));
    }
}
