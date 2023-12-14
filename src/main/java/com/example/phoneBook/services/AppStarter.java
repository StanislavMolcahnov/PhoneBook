package com.example.phoneBook.services;

import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

@Service
public class AppStarter {
    private final ShowContactsService showContactsService;
    private final AddContactsService addContactsService;
    private final DeleteContactsService deleteContactsService;

    public AppStarter(ShowContactsService showContactsService, AddContactsService addContactsService, DeleteContactsService deleteContactsService) {
        this.showContactsService = showContactsService;
        this.addContactsService = addContactsService;
        this.deleteContactsService = deleteContactsService;
    }

    public void startApp() {

        while (true) {
            System.out.println("""
                    ------------------------------------
                    Available commands:
                    1. Show contacts in app memory
                    2. Show contacts from  file
                    3. Show all contacts (app memory + file)
                    4. Add a new contact in app memory
                    5. Add a new contact to a file
                    6. Delete a contact by e-mail from app memory
                    7. Delete a contact by e-mail from file
                    8. Delete contact everywhere (app memory + file)
                    9. Save contacts from app memory to file
                    10. Remove invalid lines from file
                    11. Exit
                    Enter a command number:""");
            Scanner in = new Scanner(System.in);
            int input;

            try {
                input = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid command!");
                continue;
            }

            if (input == 1) {
                showContactsService.showContactsFromAppMemory();
            } else if (input == 2) {
                showContactsService.showContactsFromFile();
            } else if (input == 3) {
                showContactsService.showAllContacts();
            } else if (input == 4) {
                addContactsService.addContactToAppMemory();
            } else if (input == 5) {
                addContactsService.addContactToFile();
            } else if (input == 6) {
                deleteContactsService.deleteContactFromAppMemory(deleteContactsService.getEmailForDeleteContact());
            } else if (input == 7) {
                deleteContactsService.deleteContactFromFile(deleteContactsService.getEmailForDeleteContact());
            } else if (input == 8) {
                deleteContactsService.deleteContactEverywhere(deleteContactsService.getEmailForDeleteContact());
            } else if (input == 9) {
                addContactsService.addContactsFormAppMemoryToFile();
            } else if (input == 10) {
                deleteContactsService.removeInvalidLinesFromFile();
            }else if (input == 11) {
                return;
            } else {
                System.out.println("Invalid command  number!");
            }
        }
    }
}
