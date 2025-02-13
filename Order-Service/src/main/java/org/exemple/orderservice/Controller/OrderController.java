package org.exemple.orderservice.Controller;

import org.exemple.orderservice.entities.Order;
import org.exemple.orderservice.repositories.OrderRepository;
import org.exemple.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @PostMapping("save")
    public Order save(@RequestBody Order order) {
        return orderService.createOrder(order);
    }
    @GetMapping
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}
