package org.exemple.elasticsearch.Events.EventEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.exemple.elasticsearch.DTOs.Products.ProductDTO;
import org.exemple.elasticsearch.entities.Product;
@Data
@AllArgsConstructor
public class ProductAddedEvent {
    private final ProductDTO productDTO;

}
