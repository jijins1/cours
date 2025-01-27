package ovh.ruokki.example.basic.config;

import static org.junit.jupiter.api.Assertions.*;

import java.net.PasswordAuthentication;

import ovh.ruokki.example.basic.entity.Utilisateur;
import ovh.ruokki.example.basic.repository.UtilisateurRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {
    
    
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UtilisateurRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    
    public void itShouldAllowUser()
            throws Exception {
        //Given
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setLogin("toto");
        utilisateur.setPassword(passwordEncoder.encode("tata"));
        utilisateur.setId(0l);
        repository.save(utilisateur);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/bieres")
                        .contentType(MediaType.APPLICATION_JSON)
                        // toto:tata
                        .header("Authorization","Basic dG90bzp0YXRh"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        
        //then
    }
    
    
}