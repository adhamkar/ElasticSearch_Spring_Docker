package org.exemple.orderservice.repositories;

import org.exemple.orderservice.entities.OrderItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface OrderItemRepository extends ElasticsearchRepository<OrderItem,String> {
    List<OrderItem> findAll();
}
