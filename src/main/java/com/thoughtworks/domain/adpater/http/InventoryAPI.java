package com.thoughtworks.domain.adpater.http;

import com.thoughtworks.domain.command.AddSKUCommand;
import com.thoughtworks.domain.command.InitializeItemCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/inventory")
public class InventoryAPI {

    private CommandGateway commandGateway;

    public InventoryAPI(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping(path = "item")
    public CompletableFuture<Object> create(@RequestBody Map<String, String> request) {
        return commandGateway.send(new InitializeItemCommand(request.get("itemId"), request.get("description")));
    }


    @PostMapping(path="item/{itemId}/sku")
    public CompletableFuture<Object> addSKU(@PathVariable String itemId, @RequestBody Map<String, String> request) {
        return commandGateway.send(new AddSKUCommand(itemId, request.get("sku")));
    }
}
