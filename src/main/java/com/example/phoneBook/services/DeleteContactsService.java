package com.example.phoneBook.services;

import com.example.phoneBook.Storage;
import com.example.phoneBook.dto.ContactDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

@Service
public class DeleteContactsService {

    @Value("${file.path}")
    private String filePath;
    private final Storage storage;
    private final AddContactsService addContactsService;

    public DeleteContactsService(Storage storage, AddContactsService addContactsService) {
        this.storage = storage;
        this.addContactsService = addContactsService;
    }

    public void deleteContactFromAppMemory(String mailToDelete) {
        storage.deleteContactFromMap(mailToDelete);
    }

    public void deleteContactFromFile(String mailToDelete) {
        Map<String, ContactDto> contacts = addContactsService.getContactsFromFile();
        if (contacts.containsKey(mailToDelete)) {
            contacts.remove(mailToDelete);
            System.out.println("Contact deleted from file!");
        } else {
            System.out.println("There is no user with this email in the file!");
            return;
        }
        clearFile();
        FileWriter fw;
        for (ContactDto contact : contacts.values()) {
            StringBuilder lineForRecord = new StringBuilder();
            if (!addContactsService.checkFileLength()) {
                lineForRecord.append("\n");
            }
            try {
                fw = new FileWriter(filePath, true);
                if (contact.isValid()) {
                    lineForRecord.append(contact.getName())
                            .append(";")
                            .append(contact.getPhoneNumber())
                            .append(";")
                            .append(contact.getEmail());
                    fw.write(lineForRecord.toString());
                } else {
                    lineForRecord.append(contact.getWrongLine());
                    fw.write(lineForRecord.toString());
                }
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeInvalidLinesFromFile() {
        Map<String, ContactDto> contacts = addContactsService.getContactsFromFile();
        clearFile();
        for (ContactDto contact : contacts.values()) {
            addContactsService.addContactToFile(contact);
        }
        System.out.println("Invalid lines removed!");
    }

    public void deleteContactEverywhere(String mailToDelete) {
        deleteContactFromFile(mailToDelete);
        deleteContactFromAppMemory(mailToDelete);
    }

    public String getEmailForDeleteContact() {
        System.out.println("Enter email to delete a contact:");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public void clearFile() {
        FileWriter fw;
        try {
            fw = new FileWriter(filePath);
            fw.write("");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
