package com.mycompany.library10;

public class Material {
    public String code;
    public String type;
    public String name;
    public String author;
    public int quantity;

    public Material(String code, String type, String name, String author, int quantity) {
        this.code = code;
        this.type = type;
        this.name = name;
        this.author = author;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Code: " + code + ", Type: " + type + ", Name: " + name + ", Author: " + author + ", Quantity: " + quantity;
    }
}
