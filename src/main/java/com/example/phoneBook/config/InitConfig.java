package com.example.phoneBook.config;

import com.example.phoneBook.Storage;
import com.example.phoneBook.services.AddContactsService;
import com.example.phoneBook.services.BeforeStart;
import com.example.phoneBook.services.BeforeStartInit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("init")
public class InitConfig {

    private final AddContactsService addContactsService;
    private final Storage storage;

    public InitConfig(AddContactsService addContactsService, Storage storage) {
        this.addContactsService = addContactsService;
        this.storage = storage;
    }

    @Bean
    public BeforeStart beforeStart() {
        return new BeforeStartInit(addContactsService, storage);
    }
}
