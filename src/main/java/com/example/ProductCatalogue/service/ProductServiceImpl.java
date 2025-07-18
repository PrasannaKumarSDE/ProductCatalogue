package com.example.ProductCatalogue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProductCatalogue.Repository.ProductRepository;
import com.example.ProductCatalogue.dto.ProductDTO;
import com.example.ProductCatalogue.entity.Product;
import com.example.ProductCatalogue.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Convert DTO to Entity
    private Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setStockQuantity(dto.getStockQuantity());
        product.setImageUrl(dto.getImageUrl());
        return product;
    }

    // Convert Entity to DTO
    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }

    // ✅ Create Product
    @Override
    public ProductDTO create(ProductDTO dto) {
        Product product = toEntity(dto);
        Product saved = productRepository.save(product);
        return toDTO(saved);
    }

    // ✅ Get All Products
    @Override
    public List<ProductDTO> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get Product By ID
    @Override
    public ProductDTO getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return toDTO(product);
    }

    // ✅ Update Product
    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setCategory(dto.getCategory());
        existing.setStockQuantity(dto.getStockQuantity());
        existing.setImageUrl(dto.getImageUrl());

        Product updated = productRepository.save(existing);
        return toDTO(updated);
    }

    // ✅ Delete Product
    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id; " + id);
        }
        productRepository.deleteById(id);
    }
}
