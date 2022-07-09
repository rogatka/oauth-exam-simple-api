package com.exam.common.metrics;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component("google")
@RequiredArgsConstructor
public class GoogleServerHealthIndicator implements HealthIndicator {

    private final WebClient webClient;

    @Override
    public Health health() {
        Health health;
        try {
            health = webClient
                    .get()
                    .uri("https://www.google.com")
                    .exchangeToMono(response -> {
                        if (response.statusCode().is2xxSuccessful()) {
                            return Mono.just(Health.up().build());
                        }
                        return Mono.just(Health.down().build());
                    })
                    .block();
        } catch (Exception e) {
            health = Health.down().build();
        }
        return health;
    }
}
