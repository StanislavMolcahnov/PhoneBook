package com.example.phoneBook.services;

import com.example.phoneBook.services.BeforeStart;
import org.springframework.stereotype.Component;

@Component
public class ProfileWorker {

    private final BeforeStart beforeStart;

    public ProfileWorker(BeforeStart beforeStart) {
        this.beforeStart = beforeStart;
    }

    public void beforeStart() {
        beforeStart.beforeStart();
    }
}
