package com.example.phoneBook.config;

import com.example.phoneBook.services.BeforeStart;
import com.example.phoneBook.services.BeforeStartDefault;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class DefaultConfig {

    @Bean
    public BeforeStart beforeStart() {
        return new BeforeStartDefault();
    }
}
