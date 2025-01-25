package org.exemple.elasticsearch.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
@Document(indexName = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private Double price;
    private Long quantity;
}
