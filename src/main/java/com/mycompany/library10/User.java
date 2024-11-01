package com.mycompany.library10;

public abstract class User {
    public String id;
    public String documentNumber;
    public String documentType;
    public String name;
    public String password;

    public User(String id, String documentNumber, String documentType, String name, String password) {
        this.id = id;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Document: " + documentNumber + ", Type: " + documentType + ", Name: " + name;
    }
}
