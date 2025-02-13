package org.exemple.orderservice.service.Interfaces;

import org.exemple.orderservice.entities.OrderItem;

public interface IOrderItemService {
    OrderItem save(OrderItem orderItem);
}
