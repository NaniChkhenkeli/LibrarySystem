package org.example;

import org.openqa.selenium.devtools.v85.domstorage.model.DomStorageItemRemoved;

public abstract class User {
    protected String name;
    protected String email;
    protected String phoneNumber;
    protected IOOperation[] operations;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public User(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phoneNumber;
    }

    abstract public String toString();

    public abstract void menu(Database database, User user);
}
