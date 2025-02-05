package org.exemple.elasticsearch.Events;

import org.exemple.elasticsearch.DTOs.Products.ProductDTO;
import org.exemple.elasticsearch.Events.EventEntities.ProductAddedEvent;
import org.exemple.elasticsearch.Schedule.CategorySchedul;
import org.exemple.elasticsearch.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {
    @Autowired private CategorySchedul categorySchedul;
    @Autowired private NotificationService notificationService;

    @EventListener
    public void handleProductAddedEvent(ProductAddedEvent event) {
        categorySchedul.onChange();
        ProductDTO productDTO=event.getProductDTO();
        notificationService.notifySaving(productDTO);
    }
}
