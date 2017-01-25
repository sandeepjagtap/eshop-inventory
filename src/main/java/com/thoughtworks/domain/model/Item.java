package com.thoughtworks.domain.model;

public class Item {

    private String identifier;
    private int quantity;



    public Item(String identifier, int quantity) {
        this.identifier = identifier;
        this.quantity = quantity;
    }



    public String getIdentifier() {
        return identifier;
    }

    public int getQuantity() {
        return quantity;
    }
}
