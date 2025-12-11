package ovh.ruokki.example.badpractices.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ovh.ruokki.example.badpractices.entity.Product;
import ovh.ruokki.example.badpractices.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findProductWithLogging(1L)
                .map(List::of)
                .orElse(List.of()); // Poorly implemented - just an example
    }

    @PostMapping("/{action}")
    public String performAction(@PathVariable String action, @RequestBody Product product) {
        return productService.performAction(action.toUpperCase(), product);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.findProductWithLogging(id).orElse(null);
    }

    @PostMapping("/validate")
    public boolean validate(@RequestBody Product product) {
        return productService.validateProduct(product);
    }
}
