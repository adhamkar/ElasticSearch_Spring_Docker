package org.exemple.elasticsearch.ElasticRepo;

import org.exemple.elasticsearch.entities.Product;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"name\", \"description\", \"category\"],\"fuzziness\": \"AUTO\"}}")
    List<Product> searchByMultipleFields(String query);
    List<Product> findAll();
}
