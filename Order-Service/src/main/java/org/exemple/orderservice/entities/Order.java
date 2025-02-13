package org.exemple.orderservice.entities;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.exemple.orderservice.Enums.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "orders")
public class Order {
    @Id
    private String id;
    @Field(type = FieldType.Date)
    private LocalDateTime orderDate;
    //private Long supplierId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private Long userId;
    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;
    @Field(type = FieldType.Date)
    private LocalDateTime updatedAt;
    @Field(type = FieldType.Nested)
    private List<OrderItem> orderItems= new ArrayList<>(); ;
}
