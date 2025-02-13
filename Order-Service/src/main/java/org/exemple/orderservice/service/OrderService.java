package org.exemple.orderservice.service;

import lombok.extern.slf4j.Slf4j;
import org.exemple.orderservice.Enums.OrderStatus;
import org.exemple.orderservice.Order.event.OrderPlacedEvent;
import org.exemple.orderservice.entities.Order;
import org.exemple.orderservice.entities.OrderItem;
import org.exemple.orderservice.repositories.OrderRepository;
import org.exemple.orderservice.service.Interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderService(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void validateOrder(Order order) {
        if (order.getOrderDate() == null) {
            throw new IllegalArgumentException("Order date cannot be null");
        }

       /* if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }*/


    }

    public void calculateTotalOrderItemsPrice(Order order){
        if(!order.getOrderItems().isEmpty()){
            BigDecimal totalAmount = order.getOrderItems().stream()
                    .map(OrderItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            order.setTotalAmount(totalAmount);
        }else {
            order.setTotalAmount(BigDecimal.ZERO);
        }

    }

    @Override
    public Order createOrder(Order order) {
        //validateOrder(order);
        if(order.getId()==null) order.setId(UUID.randomUUID().toString());
        calculateTotalOrderItemsPrice(order);
        order.setOrderDate(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        Order savedOrder=orderRepository.save(order);
        //send Event to kafka topic
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
        orderPlacedEvent.setUserId(savedOrder.getUserId());
        orderPlacedEvent.setCreatedAt(savedOrder.getCreatedAt().toLocalDate());
        BigDecimal totalAmount = savedOrder.getTotalAmount();
        ByteBuffer byteBuffer = ByteBuffer.wrap(totalAmount.unscaledValue().toByteArray());
        orderPlacedEvent.setTotalAmount(byteBuffer);
        orderPlacedEvent.setStatus(savedOrder.getStatus().toString());
        log.info("Sending Event to kafka topic: {}", orderPlacedEvent);
        kafkaTemplate.send("order-placed", orderPlacedEvent);
        log.info("Order Sent to kafka topic: {}", orderPlacedEvent);
        return savedOrder;
    }
}
