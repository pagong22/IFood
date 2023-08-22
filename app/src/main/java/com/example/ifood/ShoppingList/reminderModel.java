package com.example.ifood.ShoppingList;

public class reminderModel {
    String Name;
    int Quantity;

    public reminderModel(String name, int quantity) {
        Name = name;
        Quantity = quantity;
    }

    public String getName() {
        return Name;
    }

    public int getQuantity() {
        return Quantity;
    }
}
