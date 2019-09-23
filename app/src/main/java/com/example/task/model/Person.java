package com.example.task.model;

import java.util.UUID;

public class Person {
    private UUID mID;
    private String mUser;
    private String mPass;

    public Person(String mUser, String mPass) {
        this.mID = UUID.randomUUID();
        this.mUser = mUser;
        this.mPass = mPass;
    }

    public UUID getmID() {
        return mID;
    }

    public String getmUser() {
        return mUser;
    }

    public String getmPass() {
        return mPass;
    }
}
