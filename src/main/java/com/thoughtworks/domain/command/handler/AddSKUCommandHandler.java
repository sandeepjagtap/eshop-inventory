package com.thoughtworks.domain.command.handler;

import com.thoughtworks.domain.aggregate.Item;
import com.thoughtworks.domain.command.AddSKUCommand;
import com.thoughtworks.domain.model.SKU;
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
public class AddSKUCommandHandler {

    private Repository<Item> repository;

    public AddSKUCommandHandler(AggregateFactory facotory, EventBus eventBus) {
        repository = new CachingEventSourcingRepository(facotory, (EventStore) eventBus, NoCache.INSTANCE);
    }

    @CommandHandler
    public void handle(AddSKUCommand command) {

        Aggregate<Item> cartAggregate =  repository.load(command.getItemId());
        cartAggregate.execute(item -> item.addSKU(new SKU(command.getSku(), null)));
    }
}
