package com.example.phoneBook;

import com.example.phoneBook.dto.ContactDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class Storage {

    private final Map<String, ContactDto> contactsMap = new TreeMap<>();

    public void addContactToMap(ContactDto contact) {

        boolean numberExists = false;

        for (ContactDto numberCheck : contactsMap.values()) {
            if (contact.isValid() && numberCheck.getPhoneNumber().equals(contact.getPhoneNumber())) {
                numberExists = true;
                break;
            }
        }
        if (contact.isValid() && contactsMap.containsKey(contact.getEmail())) {
            System.out.println("A user with this email already exists: " + contact.getEmail());
        } else if (numberExists) {
            System.out.println("A user with this number already exists: " + contact.getPhoneNumber());
        } else if (contact.isValid()) {
            contactsMap.put(contact.getEmail(), contact);
            System.out.println("Contact added to app memory: " + contact.getName());
        }
    }

    public void deleteContactFromMap(String email) {
        if (contactsMap.containsKey(email)) {
            contactsMap.remove(email);
            System.out.println("Contact deleted from app memory!");
        } else {
            System.out.println("There is no user with this email in the application memory!");
        }
    }

    public Map<String, ContactDto> getContactsMap() {
        return contactsMap;
    }
}
