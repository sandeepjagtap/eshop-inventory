package com.thoughtworks.domain.command.handler;

import com.thoughtworks.domain.command.AssociateSKUtoOrderItemCommand;
import com.thoughtworks.domain.aggregate.Item;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.caching.NoCache;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.CachingEventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.stereotype.Component;

@Component
public class AssociateSKUtoOrderItemCommandHandler {

    private Repository<Item> repository;

    public AssociateSKUtoOrderItemCommandHandler(AggregateFactory facotory, EventBus eventBus) {
        repository = new CachingEventSourcingRepository(facotory,(EventStore) eventBus, NoCache.INSTANCE) ;
    }

    @CommandHandler
    public void handle(AssociateSKUtoOrderItemCommand command) {
        Aggregate<Item> cartAggregate =  repository.load(command.getIdentifier());
        cartAggregate.execute(item -> item.allocateToOrder(command.getQuantity(),command.getOrderId()));
    }
}
