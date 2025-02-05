package org.exemple.elasticsearch.service;

import org.exemple.elasticsearch.DTOs.Products.ProductDTO;
import org.exemple.elasticsearch.entities.Product;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Async
    public void notifySaving(ProductDTO productDTO) {
        System.out.println("Product with ID "+productDTO.getId()+" has been added successfully \n"+productDTO.toString());
    }
    @Async
    public void notifyAll(List<ProductDTO> productDTOList) {
        System.out.println("There's "+productDTOList.size()+" products in total");
    }
}
