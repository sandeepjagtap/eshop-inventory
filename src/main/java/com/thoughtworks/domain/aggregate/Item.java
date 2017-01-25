package com.thoughtworks.domain.aggregate;

import com.thoughtworks.domain.command.InitializeItemCommand;
import com.thoughtworks.domain.event.ItemInitializedEvent;
import com.thoughtworks.domain.event.SKUAddedEvent;
import com.thoughtworks.domain.event.SKUAllocatedToOrderItemEvent;
import com.thoughtworks.domain.model.SKU;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Item {

    @AggregateIdentifier
    private String id;

    private List<SKU> skus;


    //needed by axonframework
    public Item() {

    }

    @CommandHandler
    public Item(InitializeItemCommand command) {
        apply(new ItemInitializedEvent(command.getId(), command.getDescription()));
    }

    public void addSKU(SKU sku) {
        apply(new SKUAddedEvent(sku.getId()));

    }

    public void allocateToOrder(int quantity, String orderId) {
        apply(new SKUAllocatedToOrderItemEvent(id, Arrays.asList(skus.get(0), skus.get(1)), orderId));
    }

    @EventSourcingHandler
    public void on(ItemInitializedEvent event){
        this.id = event.getItemId();
        skus = new ArrayList<>();
    }

    @EventSourcingHandler
    public void on(SKUAllocatedToOrderItemEvent event){

    }

    @EventSourcingHandler
    public void on(SKUAddedEvent event){
        skus.add(new SKU(event.getId(), null));
    }

}
