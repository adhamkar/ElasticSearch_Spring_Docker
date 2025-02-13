package org.exemple.elasticsearch.Events.Listner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exemple.elasticsearch.Order.OrderPlacedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryListenerrService {
    @KafkaListener(topics = "order-placed")
    public void listener(OrderPlacedEvent orderPlacedEvent) {
        log.info("Order content is sent :"+orderPlacedEvent.toString());
    }
}
