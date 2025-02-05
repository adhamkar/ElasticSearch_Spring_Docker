package org.exemple.elasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.exemple.elasticsearch.ElasticRepo.ProductRepository;
import org.exemple.elasticsearch.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    private static final String ORDER_PRODUCT_RESPONSE_TOPIC = "order-product-response";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @KafkaListener(topics = "order-product-request", groupId = "inventory-group")
    public void listenToProductRequests(String productIdsJson) throws JsonProcessingException {
        // Deserialize product IDs
        List<String> productIds = objectMapper.readValue(productIdsJson, new TypeReference<>() {});

        // Fetch product details from Elasticsearch
        List<Product> products = productIds.stream()
                .map(productRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        // Send product details back to Kafka
        String productsJson = objectMapper.writeValueAsString(products);
        kafkaTemplate.send(ORDER_PRODUCT_RESPONSE_TOPIC, productsJson);

        System.out.println("Sent product details to Kafka: " + productsJson);
    }
}
