package org.exemple.orderservice.entities;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Document(indexName = "order-item")
    public class OrderItem {
        @Id
        private String id;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private String orderId;
        private String productId;
    }
