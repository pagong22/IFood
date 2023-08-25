package com.example.ifood.ShoppingList;

public class shoppingList_Model {
    String Name;
    String Quantity;

    public shoppingList_Model(String name, String quantity) {
        Name = name;
        Quantity = quantity;
    }

    public String getName() {
        return Name;
    }

    public String getQuantity() {
        return Quantity;
    }
}
