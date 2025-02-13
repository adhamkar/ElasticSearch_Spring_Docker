package org.exemple.orderservice.Scheduler;

import org.elasticsearch.search.DocValueFormat;
import org.exemple.orderservice.entities.Order;
import org.exemple.orderservice.entities.OrderItem;
import org.exemple.orderservice.repositories.OrderItemRepository;
import org.exemple.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderSchedule {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private boolean dataChange=true;
    private List<OrderItem> lastOrderItemState = new ArrayList<>();

    public OrderSchedule(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }
    @Async("taskExecutor")
    @Scheduled(fixedDelay = 2000)
    public void addOrderItemsToOrder(){
        if(!dataChange){
            return;
        }

        List<OrderItem> orderItems = (List<OrderItem>) orderItemRepository.findAll();
        List<Order> ordersList= (List<Order>) orderRepository.findAll();

        if (orderItems.equals(lastOrderItemState)) {
            dataChange = false;
            return;
        }
        lastOrderItemState =new ArrayList<>(orderItems);

        // Group products by categoryId
        Map<String, List<OrderItem>> orderItemsByOrderId = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderId));

        // Update each category with its products
        ordersList.forEach(order -> {
            List<OrderItem> orderItemsList = orderItemsByOrderId.getOrDefault(order.getId(), new ArrayList<>());
            order.setOrderItems(orderItemsList);
        });
        // Save all categories in a batch
        orderRepository.saveAll(ordersList);
        ordersList.forEach(order -> {
            BigDecimal totalAmount = BigDecimal.ZERO;
            List<OrderItem> orderItemList=order.getOrderItems();
            for (OrderItem orderItem : orderItemList) {
                totalAmount = totalAmount.add(orderItem.getTotalPrice());
            }
            order.setTotalAmount(totalAmount);
            System.out.println("Order: " + order.getOrderDate() + " with id: " + order.getId() + " has " + orderItemList.size() + " orderItems."+ "Total Amount is "+ order.getTotalAmount());

        });
        orderRepository.saveAll(ordersList);
        dataChange=false;
    }
    @Async("taskExecutor")
    public void onChange(){
        this.dataChange=true;
    }
    @Async
    //@Scheduled(fixedDelay = 5000)
    public void calculateTotalAmountOnDataChange(){
        if(!dataChange){
            return;
        }

    }

}
