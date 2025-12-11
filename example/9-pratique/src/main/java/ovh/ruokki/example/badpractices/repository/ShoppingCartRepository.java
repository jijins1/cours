package ovh.ruokki.example.badpractices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.ruokki.example.badpractices.entity.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
