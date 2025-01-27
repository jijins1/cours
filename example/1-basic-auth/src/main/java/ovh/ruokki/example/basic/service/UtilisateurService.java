package ovh.ruokki.example.basic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
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
public class UtilisateurService implements UserDetailsService  {
    private static Logger log = LoggerFactory.getLogger(UtilisateurService.class);
    @Autowired
    UtilisateurRepository repository;
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        Optional<Utilisateur> res = repository.findByLogin(username);
        if(res.isPresent()){
            return new User(res.get().getLogin(),res.get().getPassword(),new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("L'utilisateur n'existe pas");
        }
        
    }
    
}
