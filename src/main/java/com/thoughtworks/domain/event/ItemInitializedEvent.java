package com.thoughtworks.domain.event;

public class ItemInitializedEvent {
    private final String itemId;

    private final String description;

    public ItemInitializedEvent(String itemId, String description) {
        this.itemId = itemId;
        this.description = description;
    }

    public String getItemId() {
        return itemId;
    }

    public String getDescription() {
        return description;
    }
}
