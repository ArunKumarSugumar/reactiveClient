package com.reactiveclient.controller;

import com.reactiveclient.domain.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * @author ArunKumar.Sugumar
 */
@RestController
public class ItemClientController {

    WebClient webclient = WebClient.create("http://localhost:8080");

    @GetMapping("/client/retrieve")
    public Flux<Item> getAllItemsUsingRetrieve() {
        return webclient.get().uri("/v1/item")
                .retrieve()
                .bodyToFlux(Item.class)
                .log("Items in client project retrieve");
    }

    @GetMapping("/client/exchange")
    public Flux<Item> getAllItemsUsingExchange() {
        return webclient.get().uri("/v1/item")
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Item.class))
                .log("Items in client project exchange");
    }

}
