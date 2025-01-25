package org.exemple.elasticsearch.controller;

import org.exemple.elasticsearch.ElasticRepo.ProductRepository;
import org.exemple.elasticsearch.entities.Product;
import org.exemple.elasticsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
    @Autowired private ProductRepository productRepo;
    @Autowired private ProductService productService;

    @GetMapping("/products")
    public Iterable<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @PostMapping("save")
    public Product saveProduct(@RequestBody Product product) {
        if(product.getId()==null) {
            product.setId(UUID.randomUUID().toString());
        }
        return productRepo.save(product);
    }
    @GetMapping("/creteria/search")
    public List<Product> searchProduct(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) String description,
                                           @RequestParam(required = false) String category,
                                           @RequestParam(required = false) Double minProce,
                                           @RequestParam(required = false) Double maxPrice,
                                           @RequestParam(required = false)Long minQuantity) throws Exception {
    return productService.SearchEngine(name,description,category,minProce,maxPrice,minQuantity);
    }

    @GetMapping("/query/search")
    public List<Product> searchProduct(@RequestParam String query){
        return productRepo.searchByMultipleFields(query);
    }

    @DeleteMapping("/delete")
    public void deletProduct(@RequestParam String id) {
        productRepo.deleteById(id);
    }

}
