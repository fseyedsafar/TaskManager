package com.example.task.model;

import java.util.UUID;

public class User {
    private UUID mID;
    private String mUser;
    private String mPass;

    public User(){
        this(UUID.randomUUID());
    }

    public User(UUID mID){
        this.mID = mID;
    }

    public User(String mUser, String mPass) {
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

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public void setmPass(String mPass) {
        this.mPass = mPass;
    }
}
