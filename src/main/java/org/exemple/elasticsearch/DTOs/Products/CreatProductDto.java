package org.exemple.elasticsearch.DTOs.Products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatProductDto {
    private String name;
    private String description;
    private String categoryId;
    private Double price;
    private Long quantity;
}
