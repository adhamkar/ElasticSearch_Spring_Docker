package org.exemple.orderservice.Events;

import org.exemple.orderservice.Events.Entities.OrderItemAdded;
import org.exemple.orderservice.Scheduler.OrderSchedule;
import org.exemple.orderservice.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListner {
    @Autowired private OrderSchedule orderSchedule;
    @EventListener
    public void handleOrderItemAddedEvent(OrderItemAdded event){
        orderSchedule.onChange();
    }
}
