package org.exemple.elasticsearch.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.exemple.elasticsearch.enums.CategoryType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "categories")
public class Category {
    @Id
    private String id;
    private String name;
    private CategoryType type;
    @Field(type = FieldType.Nested)
    private List<Product> products;
}
