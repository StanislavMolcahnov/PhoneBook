package com.example.phoneBook.services;

import org.springframework.stereotype.Service;

@Service
public class BeforeStartDefault implements BeforeStart {

    @Override
    public void beforeStart() {
        System.out.println("App starting...");
    }
}
