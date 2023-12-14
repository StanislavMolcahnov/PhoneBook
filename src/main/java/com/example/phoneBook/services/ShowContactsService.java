package com.example.phoneBook.services;

import com.example.phoneBook.Storage;
import com.example.phoneBook.dto.ContactDto;
import org.springframework.stereotype.Service;

@Service
public class ShowContactsService {

    private final Storage storage;

    private final AddContactsService addContactsService;

    public ShowContactsService(Storage storage, AddContactsService addContactsService) {
        this.storage = storage;
        this.addContactsService = addContactsService;
    }

    public void showContactsFromAppMemory() {
        System.out.println("Contacts from app memory:");
        if (storage.getContactsMap().isEmpty()) {
            System.out.println("Contacts list from app memory is empty!");
        } else {
            for (String email : storage.getContactsMap().keySet()) {
                displayContacts(storage.getContactsMap().get(email));
            }
        }
    }

    public void showContactsFromFile() {
        System.out.println("Contacts from file:");
        if (addContactsService.checkFileLength()) {
            System.out.println("Contact list from file is empty!");
        }
        for (ContactDto contactDto : addContactsService.getContactsFromFile().values()) {
            if (contactDto.isValid()) {
                displayContacts(contactDto);
            } else {
                addContactsService.showErrors(contactDto.getWrongLine());
            }
        }
    }

    public void displayContacts(ContactDto contact) {
        System.out.println(contact.getName() + " | " + contact.getPhoneNumber() + " | " + contact.getEmail());
    }

    public void showAllContacts() {
        showContactsFromFile();
        System.out.println("---");
        showContactsFromAppMemory();
    }


}
