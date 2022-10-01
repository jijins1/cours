package ovh.ruokki.example.role.repository;

import java.util.Optional;

import ovh.ruokki.example.role.entity.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByLogin(final String email);
}
