package com.thoughtworks.domain.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class AssociateSKUtoOrderItemCommand {

    @TargetAggregateIdentifier
    private final String identifier;

    private final int quantity;
    private String orderId;

    public AssociateSKUtoOrderItemCommand(String identifier, int quantity, String orderId) {

        this.identifier = identifier;
        this.quantity = quantity;
        this.orderId = orderId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrderId() {
        return orderId;
    }
}
