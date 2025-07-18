package com.example.ProductCatalogue.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "index";  // Loads index.html from templates/
    }

    @GetMapping("/product-form")
    public String productForm() {
        return "product_form";  // Loads product_form.html from templates/
    }
}
