package org.exemple.elasticsearch.controller;

import org.exemple.elasticsearch.DTOs.Products.ProductDTO;
import org.exemple.elasticsearch.ElasticRepo.CategoryRepository;
import org.exemple.elasticsearch.ElasticRepo.ProductRepository;
import org.exemple.elasticsearch.Mappers.ProductMapper;
import org.exemple.elasticsearch.entities.Category;
import org.exemple.elasticsearch.entities.Product;
import org.exemple.elasticsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {
    @Autowired private ProductRepository productRepo;
    @Autowired private ProductService productService;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ProductMapper productMapper;

    @GetMapping("/all")
    public List<ProductDTO> getAllProducts() {
        return productService.findAll();
    }

    @PostMapping("save")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO,@RequestParam String categoryId) {
        if(productDTO.getId()==null) {
            productDTO.setId(UUID.randomUUID().toString());
        }

        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found");
        }
        productDTO.setCategoryId(categoryId);
        return productService.createProduct(productDTO);
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

    @DeleteMapping("/delete/{id}")
    public void deletProduct(@PathVariable String id) {
        productRepo.deleteById(id);
    }

}
