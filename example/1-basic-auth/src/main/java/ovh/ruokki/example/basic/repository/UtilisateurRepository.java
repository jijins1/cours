package ovh.ruokki.example.basic.repository;

import java.util.Optional;

import ovh.ruokki.example.basic.entity.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByLogin(final String email);
}
