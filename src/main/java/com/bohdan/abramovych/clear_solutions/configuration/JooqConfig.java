package com.baybut.cocacola.stock.configuration;

import org.jooq.conf.Settings;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = R2dbcAutoConfiguration.class)
public class JooqConfig {

    @Bean
    Settings jooqSettings() {
        return new Settings()
                .withExecuteWithOptimisticLocking(true);
    }
}
