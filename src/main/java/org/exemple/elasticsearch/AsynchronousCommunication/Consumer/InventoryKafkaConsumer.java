package org.exemple.elasticsearch.AsynchronousCommunication.Consumer;

import lombok.AllArgsConstructor;
import org.exemple.elasticsearch.service.ProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryKafkaConsumer {
    private final ProductService productService;

//    @KafkaListener(topics = "order_created_topic",groupId = "inventory-group")
//    public void consumeOrderEvent(OrderCreatedEvent event) {
//        System.out.println("Received Order: " + event.getOrderId());
//
//        event.getItems().forEach(item -> {
//            productService.updateStock(item.getProductId(), item.getQuantity());
//        });
//    }

}
