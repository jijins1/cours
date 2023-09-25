package ovh.ruokki.example.basic.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import ovh.ruokki.example.basic.entity.Utilisateur;
import ovh.ruokki.example.basic.repository.UtilisateurRepository;

@Service
public class UtilisateurService implements UserDetailsService {
    private static Logger log = LoggerFactory.getLogger(UtilisateurService.class);
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(final UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Pour l'exemple j'enregistre un utilisateur au demarrage
     */
    @PostConstruct
    public void createUserDefault() {
        log.info("Creation du user test");
        // en base 64 : dXNlcjpwYXNzd29yZA==
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setLogin("user");
        utilisateur.setPassword(passwordEncoder.encode("password"));
        this.utilisateurRepository.save(utilisateur);
    }

    @Override
    public UserDetails loadUserByUsername(final String login)
            throws UsernameNotFoundException {
        log.info("recuperation de {}", login);

        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByLogin(login);
        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            return new User(utilisateur.getLogin(), utilisateur.getPassword(), List.of());
        } else {
            throw new UsernameNotFoundException("L'utilisateur" + login + " n'existe pas");
        }

    }
}
