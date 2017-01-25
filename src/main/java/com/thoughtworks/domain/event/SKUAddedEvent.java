package com.thoughtworks.domain.event;

public class SKUAddedEvent {
    private String id;

    public SKUAddedEvent(String id) {

        this.id = id;
    }

    public String getId() {
        return id;
    }
}
