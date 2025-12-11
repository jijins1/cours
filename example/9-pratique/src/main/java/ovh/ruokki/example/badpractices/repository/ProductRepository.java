package ovh.ruokki.example.badpractices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.ruokki.example.badpractices.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
