package org.exemple.orderservice.repositories;

import org.exemple.orderservice.entities.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface OrderRepository extends ElasticsearchRepository<Order, String> {
    List<Order> findAll();
}
