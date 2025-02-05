package org.exemple.elasticsearch.service;



import org.exemple.elasticsearch.DTOs.Products.ProductDTO;
import org.exemple.elasticsearch.ElasticRepo.ProductRepository;
import org.exemple.elasticsearch.Events.EventEntities.ProductAddedEvent;
import org.exemple.elasticsearch.Mappers.ProductMapper;
import org.exemple.elasticsearch.Schedule.CategorySchedul;
import org.exemple.elasticsearch.entities.Product;
import org.exemple.elasticsearch.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    @Autowired
    ProductMapper productMapper;
    @Autowired private ProductRepository productRepository;
    @Autowired private NotificationService notificationService;
    @Autowired private CategorySchedul categorySchedul;
    @Autowired private ApplicationEventPublisher eventPublisher;


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

    @Override
    public List<ProductDTO> findAll() {
        Iterable<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach(product -> {
            productDTOs.add(productMapper.productToProductDTO(product));
        });
        notificationService.notifyAll(productDTOs);
        return productDTOs;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        Product savedProduct=productRepository.save(product);
        eventPublisher.publishEvent(new ProductAddedEvent(productDTO));
        return productMapper.productToProductDTO(savedProduct);
    }


}
