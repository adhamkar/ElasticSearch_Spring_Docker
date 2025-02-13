package org.exemple.orderservice.service;

import org.exemple.orderservice.entities.OrderItem;
import org.exemple.orderservice.repositories.OrderItemRepository;
import org.exemple.orderservice.repositories.OrderRepository;
import org.exemple.orderservice.service.Interfaces.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderItemService implements IOrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public OrderItem save(OrderItem orderItem) {
        validateOrderItem(orderItem);
        calculateTotalPrice(orderItem);
        if(orderItem.getId()==null) orderItem.setId(UUID.randomUUID().toString());
        return orderItemRepository.save(orderItem);
    }
    private void validateOrderItem(OrderItem orderItem) {
        if (orderItem.getProductId() == null || orderItem.getProductId().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }

        if (orderItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        if (orderItem.getUnitPrice() == null || orderItem.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }

    }
    private void calculateTotalPrice(OrderItem orderItem) {
        BigDecimal totalPrice = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
        orderItem.setTotalPrice(totalPrice);
    }
}
