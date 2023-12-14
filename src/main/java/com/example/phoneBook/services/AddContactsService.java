package com.example.phoneBook.services;

import com.example.phoneBook.Storage;
import com.example.phoneBook.dto.ContactDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class AddContactsService {

    @Value("${file.path}")
    private String filePath;
    private final Storage storage;

    public AddContactsService(Storage storage) {
        this.storage = storage;
    }


    public void addContactToAppMemory() {
        String contactLine = readString();
        ContactDto contactDto = contactLineCheck(contactLine);
        showErrors(contactLine);
        if (contactDto.isValid()) {
            storage.addContactToMap(contactDto);
        }
    }

    public ContactDto contactLineCheck(String contactLine) {

        ContactDto contact = new ContactDto();
        String[] contactMassive = contactLine.split(";");

        if (contactMassive.length == 3 &&
                contactMassive[1].trim().matches("[+]\\d+") &&
                contactMassive[2].trim().matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")
        ) {
            contact.setName(contactMassive[0].trim());
            contact.setPhoneNumber(contactMassive[1].trim());
            contact.setEmail(contactMassive[2].trim());
            contact.setValid(true);
        }
        return contact;
    }

    public void addContactsFormAppMemoryToFile() {
        Map<String, ContactDto> contactsFromAppMemory = storage.getContactsMap();
        if (contactsFromAppMemory.isEmpty()) {
            System.out.println("There are no contacts in the application memory to add!");
        } else {
            for (ContactDto contactDto : contactsFromAppMemory.values()) {
                addContactToFile(contactDto);
            }
            System.out.println("Contacts from the app memory have been added to the file!");
        }
    }

    public void addContactToFile() {
        String contactLine = readString();
        ContactDto contact = contactLineCheck(contactLine);
        showErrors(contactLine);
        if (addContactToFile(contact)) {
            System.out.println("Contact added to file!");
        }

    }

    public void showErrors(String contactLine) {
        String[] contactMassive = contactLine.split(";");

        if (contactMassive.length != 3) {
            System.out.println("Contact entered incorrectly: " + contactLine);
        } else if (!contactMassive[1].trim().matches("[+]\\d+")) {
            System.out.println("Wrong phone number: " + contactLine);
        } else if (!contactMassive[2].trim().matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")) {
            System.out.println("Wrong email: " + contactLine);
        }
    }

    public Map<String, ContactDto> getContactsFromFile() {
        Map<String, ContactDto> contacts = new HashMap<>();
        FileReader fr;
        try {
            fr = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exists!");
            return contacts;
        }
        Scanner scanner = new Scanner(fr);
        long i = 0;
        while (scanner.hasNextLine()) {
            String contactLine = scanner.nextLine();
            ContactDto contact = contactLineCheck(contactLine);
            if (contact.isValid() && !contacts.containsKey(contact.getEmail())) {
                contacts.put(contact.getEmail(), contact);
            } else if (contacts.containsKey(contact.getEmail())) {
                contacts.put(String.valueOf(i), contact);
                i++;
            } else {
                ContactDto contactDto = new ContactDto();
                contactDto.setWrongLine(contactLine);
                contacts.put(contactDto.getWrongLine(), contactDto);
            }
        }
        try {
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contacts;
    }

    public boolean checkFileLength() {
        File file = new File(filePath);
        return file.length() == 0;
    }

    private String readString() {
        System.out.println("Enter the contact in the format:\n" +
                "Full name;+111111111111;email");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public boolean addContactToFile(ContactDto contact) {
        Map<String, ContactDto> contacts = getContactsFromFile();
        String contactForFile = contact.getName() + ";" + contact.getPhoneNumber() + ";" + contact.getEmail();
        boolean numberExists = false;

        for (ContactDto numberCheck : contacts.values()) {
            if (numberCheck.getPhoneNumber() != null && numberCheck.getPhoneNumber().equals(contact.getPhoneNumber())) {
                numberExists = true;
                break;
            }
        }
        if (contacts.containsKey(contact.getEmail())) {
            System.out.println("A user with this email already exists: " + contact.getEmail());
        } else if (numberExists) {
            System.out.println("A user with this number already exists: " + contact.getPhoneNumber());
        } else {
            File file = new File(filePath);
            if (file.length() != 0) {
                contactForFile = "\n" + contactForFile;
            }
            if (contact.isValid()) {
                FileWriter fw;
                try {
                    fw = new FileWriter(filePath, true);
                    fw.write(contactForFile);
                    fw.close();
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }
}
