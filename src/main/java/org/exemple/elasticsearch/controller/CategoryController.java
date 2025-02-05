package org.exemple.elasticsearch.controller;

import org.exemple.elasticsearch.ElasticRepo.CategoryRepository;
import org.exemple.elasticsearch.ElasticRepo.ProductRepository;
import org.exemple.elasticsearch.entities.Category;
import org.exemple.elasticsearch.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/all")
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    @PostMapping("/save")
    public Category SaveCategory(@RequestBody Category category) {

        if(category.getId()!=null) category.setId(UUID.randomUUID().toString());
        return categoryRepository.save(category);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable String id) {
        categoryRepository.deleteById(id);
    }
    @GetMapping("/search")
    public List<Category> search(@RequestParam String query) {
        return categoryRepository.searchByNameAndType(query);
    }@GetMapping("/search/fuzzy")
    public List<Category> fuzzySearch(@RequestParam String query) {
        return categoryRepository.searchMultipleFields(query);
    }
    @PostMapping("/addProductsToCategory/categoryId/{categoryId}")
    public Category addProductsToCategory(@RequestParam String categoryId,@RequestBody Product product) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        if(product.getId()==null) {
            product.setId(UUID.randomUUID().toString());
        }
        product.setCategoryId(category.getId());
        productRepository.save(product);
        List<Product> products=List.of(product);
        category.setProducts(products);
        return category;
    }
}
