package ovh.ruokki.example.badpractices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.ruokki.example.badpractices.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
