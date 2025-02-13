package org.exemple.elasticsearch.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private String status;
    private BigDecimal totalAmount;
    private Long userId;
    private Date createdAt;
}
