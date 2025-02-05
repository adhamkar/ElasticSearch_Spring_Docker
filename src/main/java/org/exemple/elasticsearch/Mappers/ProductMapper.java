package org.exemple.elasticsearch.Mappers;

import org.exemple.elasticsearch.DTOs.Products.CreatProductDto;
import org.exemple.elasticsearch.DTOs.Products.ProductDTO;
import org.exemple.elasticsearch.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    @Autowired private ModelMapper modelMapper;

    public ProductDTO productToProductDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
    public Product productDTOToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
    public Product ToProductFromCreateProductDTO(CreatProductDto productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
