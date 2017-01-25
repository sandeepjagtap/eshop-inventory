package com.thoughtworks.domain.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class InitializeItemCommand {

    @TargetAggregateIdentifier
    private String id;

    private String description;



    public String getDescription() {
        return description;
    }

    public InitializeItemCommand(String itemId, String description) {
        this.id = itemId;
        this.description = description;
    }

    public String getId() {
        return id;
    }


}
