package ovh.ruokki.example.badpractices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ovh.ruokki.example.badpractices.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
