package com.exam.common.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MetricsConfiguration {
    private final MeterRegistry meterRegistry;

    @Bean
    public Counter oidcCounter() {
        return Counter.builder("oidc.authentications")
                .description("Number of Oidc authentications")
                .register(meterRegistry);
    }
}
