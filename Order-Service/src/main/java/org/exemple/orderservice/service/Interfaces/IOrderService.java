package org.exemple.orderservice.service.Interfaces;

import org.exemple.orderservice.entities.Order;

public interface IOrderService {
    Order  createOrder(Order order);
}
