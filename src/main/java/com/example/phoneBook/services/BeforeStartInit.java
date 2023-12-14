package com.example.phoneBook.services;

import com.example.phoneBook.Storage;
import com.example.phoneBook.dto.ContactDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BeforeStartInit implements BeforeStart {

    private final AddContactsService addContactsService;
    private final Storage storage;

    public BeforeStartInit(AddContactsService addContactsService, Storage storage) {
        this.addContactsService = addContactsService;
        this.storage = storage;
    }

    @Override
    public void beforeStart() {
        System.out.println("App starting...\n" +
                "Init contacts from file!");
        Map<String, ContactDto> contacts = addContactsService.getContactsFromFile();
        for (ContactDto contactDto : contacts.values()) {
            storage.addContactToMap(contactDto);
        }
    }
}
