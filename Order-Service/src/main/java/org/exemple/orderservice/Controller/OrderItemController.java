package org.exemple.orderservice.Controller;

import org.exemple.orderservice.entities.OrderItem;
import org.exemple.orderservice.repositories.OrderItemRepository;
import org.exemple.orderservice.repositories.OrderRepository;
import org.exemple.orderservice.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orderItems")
public class OrderItemController {
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;
    @PostMapping("/save")
    public void addOrderItem(@RequestBody OrderItem orderItem,@RequestParam String orderId){
        if(orderItem.getId()==null) {
            orderItem.setId(UUID.randomUUID().toString());
        }

        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderItem.setOrderId(orderId);
        orderItemService.save(orderItem);
    }
    @GetMapping("/all")
    public List<OrderItem> getAllOrderItems(){
        return orderItemRepository.findAll();
    }
}
