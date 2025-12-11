package ovh.ruokki.example.badpractices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ovh.ruokki.example.badpractices.entity.Product;
import ovh.ruokki.example.badpractices.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductProcessor processor = new BasicProductProcessor(true, true);  // Hardcoded with validation and logging

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String performAction(String action, Product product) {
        switch (action) {
            case "CREATE":
                if (product.getPrice() <= 0) {
                    throw new IllegalArgumentException("Price must be positive");
                }
                productRepository.save(product);
                System.out.println("Created product: " + product.getName());
                return "Product created";
            case "UPDATE":
                if (product.getId() == null) {
                    throw new IllegalArgumentException("ID required for update");
                }
                productRepository.save(product);
                System.out.println("Updated product: " + product.getName());
                return "Product updated";
            case "DELETE":
                if (product.getId() == null) {
                    throw new IllegalArgumentException("ID required for delete");
                }
                productRepository.deleteById(product.getId());
                System.out.println("Deleted product: " + product.getId());
                return "Product deleted";
            case "DISCOUNT":
                if (product.getPrice() > 0) {
                    product.setPrice(product.getPrice() * 0.9);
                    productRepository.save(product);
                }
                return "Discount applied";
            case "PROCESS":
                return processor.executeProcessing(product.getType(), product.getPrice(), "GENERAL");
            case "SEARCH":
                List<Product> products = productRepository.findAll();
                System.out.println("Found " + products.size() + " products");
                return "Search performed";
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }
    }

    public Optional<Product> findProductWithLogging(Long id) {
        System.out.println("Looking for product with id: " + id);
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            System.out.println("Product not found");
        } else {
            System.out.println("Product found: " + product.get().getName());
        }
        return product;
    }

    public boolean validateProduct(Product product) {
        System.out.println("Validating product: " + product.getName());
        if (product.getName() == null || product.getName().isEmpty()) {
            System.out.println("Validation failed: name is empty");
            return false;
        }
        if (product.getPrice() < 0) {
            System.out.println("Validation failed: negative price");
            return false;
        }
        System.out.println("Validation passed");
        return true;
    }
}
