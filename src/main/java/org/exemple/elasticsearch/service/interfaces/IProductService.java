package org.exemple.elasticsearch.service.interfaces;

import org.exemple.elasticsearch.DTOs.Products.ProductDTO;
import org.exemple.elasticsearch.entities.Product;

import java.util.List;

public interface IProductService {
    List<ProductDTO> findAll();
    ProductDTO createProduct(ProductDTO productDTO);
}
