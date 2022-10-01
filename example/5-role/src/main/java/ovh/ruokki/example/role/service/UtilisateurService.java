package ovh.ruokki.example.role.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import ovh.ruokki.example.role.entity.Utilisateur;
import ovh.ruokki.example.role.repository.UtilisateurRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtilisateurService implements UserDetailsService {
    private static Logger log = LoggerFactory.getLogger(UtilisateurService.class);
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    
    
    @Autowired
    public UtilisateurService(final UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Pour l'exemple j'enregistre un utilisateur au demarrage
     */
    @PostConstruct
    public void createUserDefault(){
        log.info("Creation du user defaut");
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setLogin("user");
        utilisateur.setRoles(List.of("USER", "COMMERCIAL"));
        utilisateur.setPassword(passwordEncoder.encode("password"));
        this.utilisateurRepository.save(utilisateur);
        log.info("Creation du user admin");
        Utilisateur admin = new Utilisateur();
        admin.setLogin("admin");
        admin.setRoles(List.of("ADMIN", "COMMERCIAL"));
        admin.setPassword(passwordEncoder.encode("password"));
        this.utilisateurRepository.save(admin);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login)
            throws UsernameNotFoundException {
        log.info("recuperation de {}", login);
    
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByLogin(login);
        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            return new User(utilisateur.getLogin(), utilisateur.getPassword(), utilisateur.getRoles().stream().map(SimpleGrantedAuthority::new).toList());
        } else {
            throw new UsernameNotFoundException("L'utilisateur" + login + " n'existe pas");
        }

    }
}
