package com.example.splash;

import androidx.annotation.NonNull;

public class Contact {

    private int id;
    private String name;
    private String contact;

    public Contact() {
        // Default constructor
    }

    // Getters and setters for id, name, and contact fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    // Override toString() method to provide custom string representation
    @NonNull
    @Override
    public String toString() {
        return  name + "\n" + contact ;
    }
}
