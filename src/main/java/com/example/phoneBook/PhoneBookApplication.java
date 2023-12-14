package com.example.phoneBook;

import com.example.phoneBook.services.AppStarter;
import com.example.phoneBook.services.ProfileWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhoneBookApplication {

    private static AppStarter appStarter;
    private static ProfileWorker profileWorker;

    public PhoneBookApplication(AppStarter appStarter, ProfileWorker profileWorker) {
        PhoneBookApplication.appStarter = appStarter;
        PhoneBookApplication.profileWorker = profileWorker;
    }


    public static void main(String[] args) {
        SpringApplication.run(PhoneBookApplication.class, args);
        profileWorker.beforeStart();
        appStarter.startApp();
    }

}
