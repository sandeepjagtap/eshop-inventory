package com.thoughtworks.domain.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class AddSKUCommand {

    @TargetAggregateIdentifier
    private final String itemId;

    public String getSku() {
        return sku;
    }

    private final String sku;

    public AddSKUCommand(String itemId, String sku) {

        this.itemId = itemId;
        this.sku = sku;
    }

    public String getItemId() {
        return itemId;
    }
}
