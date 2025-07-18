package com.example.ProductCatalogue.service;




import java.util.List;

import com.example.ProductCatalogue.dto.ProductDTO;

public interface ProductService {
    ProductDTO create(ProductDTO dto);
    ProductDTO update(Long id, ProductDTO dto);
    void delete(Long id);
    List<ProductDTO> getAll();
    ProductDTO getById(Long id);
}
