package org.exemple.elasticsearch.Schedule;

import org.exemple.elasticsearch.ElasticRepo.CategoryRepository;
import org.exemple.elasticsearch.ElasticRepo.ProductRepository;
import org.exemple.elasticsearch.entities.Category;
import org.exemple.elasticsearch.entities.Product;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategorySchedul {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private boolean dataChange=true;
    private List<Product> lastProductState = new ArrayList<>();

    public CategorySchedul(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    //@Async
    //@Scheduled(fixedDelay = 2000)
    public void addProductToCategory() {
        Iterable<Product> products=productRepository.findAll();
        Iterable<Category> categories=categoryRepository.findAll();
        List<Category> categoryList=new ArrayList<>();
        categories.forEach(category -> {
            List<Product> productList=new ArrayList<>();
            for(Product product:products){
                if(Objects.equals(category.getId(), product.getCategoryId())){
                    productList.add(product);
                    System.out.println("product :"+product.getName()+" added to Category"+category.getName()+" with id:"+category.getId());
                    break;
                }
            }
            category.setProducts(productList);
            categoryList.add(category);
        });
        categoryRepository.saveAll(categoryList);
    }

    @Async("taskExecutor")
    @Scheduled(fixedDelay = 2000)
    public void addProductsToCategory() {
        if(!dataChange){
            return;
        }
        // Fetch all products and categories once
        List<Product> products = (List<Product>) productRepository.findAll();
        List<Category> categories = (List<Category>) categoryRepository.findAll();
        if (products.equals(lastProductState)) {
            dataChange = false;
            return;
        }
        lastProductState = new ArrayList<>(products);
        // Group products by categoryId
        Map<String, List<Product>> productsByCategoryId = products.stream()
                .collect(Collectors.groupingBy(Product::getCategoryId));

        // Update each category with its products
        categories.forEach(category -> {
            List<Product> productList = productsByCategoryId.getOrDefault(category.getId(), new ArrayList<>());
            category.setProducts(productList);
            System.out.println("Category: " + category.getName() + " with id: " + category.getId() + " has " + productList.size() + " products.");
        });

        // Save all categories in a batch
        categoryRepository.saveAll(categories);
        dataChange=false;
    }
    @Async("taskExecutor")
    public void onChange(){
        this.dataChange=true;
    }

}
