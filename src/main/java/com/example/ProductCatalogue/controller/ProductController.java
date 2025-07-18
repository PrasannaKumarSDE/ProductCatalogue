package com.example.ProductCatalogue.controller;

import com.example.ProductCatalogue.dto.ProductDTO;
import com.example.ProductCatalogue.entity.Product;
import com.example.ProductCatalogue.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

	
    @Autowired
    private ProductService productService;

    private final String uploadDir = "uploads/"; // relative directory under project root

    // POST with file upload
    @PostMapping("/upload-with-product")
    public ProductDTO uploadWithProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("category") String category,
            @RequestParam("stockQuantity") Integer stockQuantity,
            @RequestParam("file") MultipartFile file) {

        String uploadDir = "uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = UUID.randomUUID() + "_" + originalFilename;
        Path filePath = Paths.get(uploadDir + fileName);
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }

        String imageUrl = "/uploads/" + fileName;

        ProductDTO dto = new ProductDTO();
        dto.setName(name);
        dto.setDescription(description);
        dto.setPrice(price);
        dto.setCategory(category);
        dto.setStockQuantity(stockQuantity);
        dto.setImageUrl(imageUrl);

        return productService.create(dto);
    }

    // Standard CRUD methods
    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO dto) {
        return productService.create(dto);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.update(id, productDTO);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return productService.getById(id);
    }
    @GetMapping("/product_form")
    public String showUploadForm() {
        return "upload";  // upload.html in templates
    }
}

