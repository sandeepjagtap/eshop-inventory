package com.thoughtworks.ddd.bootcamp;

import com.rabbitmq.client.Channel;
import com.thoughtworks.domain.adpater.http.InventoryAPI;
import com.thoughtworks.domain.aggregate.Item;
import com.thoughtworks.domain.command.AssociateSKUtoOrderItemCommand;
import com.thoughtworks.domain.command.handler.AddSKUCommandHandler;
import com.thoughtworks.domain.event.OrderCreatedEvent;
import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.eventstore.jpa.DomainEventEntry;
import org.axonframework.serialization.Serializer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
		InventoryAPI.class,
		Item.class,
		AddSKUCommandHandler.class
})
@EntityScan(basePackageClasses = {DomainEventEntry.class})

public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

	@Component
	@ProcessingGroup("inventory")
	public static class InventoryMessageProcessor {

		private CommandGateway commandGateway;

		public InventoryMessageProcessor(CommandGateway commandGateway) {
			this.commandGateway = commandGateway;
		}

		@EventHandler
		public void handle(OrderCreatedEvent event) {

			List<com.thoughtworks.domain.model.Item> items = event.getItems();
			items.stream().map(
					item -> commandGateway.send(new AssociateSKUtoOrderItemCommand(item.getIdentifier(),
							item.getQuantity(), event.getId()))).toArray();

		}
	}

	@Bean
	public SpringAMQPMessageSource inventorySource(Serializer serlializer) {

		return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serlializer)) {
			@RabbitListener(queues = "InventoryEvents")
			@Transactional
			public void onMessage(Message message, Channel channel) throws Exception {
				super.onMessage(message, channel);
			}
		};
	}

	@Bean
	public Exchange exchange() {
		return ExchangeBuilder.fanoutExchange("appFanoutExchange").build();
	}

	@Bean
	public Queue queue() {
		return QueueBuilder.durable("InventoryEvents").build();
	}

	@Bean
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with("*").noargs();
	}

	@Autowired
	public void configure(AmqpAdmin admin) {
		admin.declareQueue(queue());
		admin.declareBinding(binding());
	}


}
