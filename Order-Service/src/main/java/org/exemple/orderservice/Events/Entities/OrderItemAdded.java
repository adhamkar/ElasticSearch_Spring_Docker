package org.exemple.orderservice.Events.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.exemple.orderservice.entities.OrderItem;

@Data
@AllArgsConstructor
public class OrderItemAdded {
    private OrderItem orderItem;
}
