package org.exemple.elasticsearch.service;


import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.exemple.elasticsearch.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    public List<Product> SearchEngine(String name, String description, String category, Double minPrice, Double maxPrice, Long minQuantity) throws Exception {
        Criteria queryCriteria = new Criteria();
        if(name!=null) queryCriteria.and("name").matches(name);
        if(description!=null) queryCriteria.and("description").matches(description);
        if(category!=null) queryCriteria.and("category").matches(category);
        if( minPrice !=null && maxPrice !=null) queryCriteria.and("price").between(minPrice, maxPrice);
        if(minQuantity!=null) queryCriteria.and("quantity").greaterThanEqual(minQuantity);
        CriteriaQuery criteriaQuery=new CriteriaQuery(queryCriteria);
        System.out.println("Executing query: " + criteriaQuery.getCriteria().toString());
        return elasticsearchOperations.search(criteriaQuery,Product.class).stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
//    public List<Product> searchByMultipleFields(String query,Long quantity) throws Exception {
//        MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(query, "name", "description", "category");
//
//    }

}
