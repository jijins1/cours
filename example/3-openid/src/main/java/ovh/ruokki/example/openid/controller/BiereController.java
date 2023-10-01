package ovh.ruokki.example.openid.controller;

import java.util.ArrayList;
import java.util.List;

import ovh.ruokki.example.openid.controller.dto.Biere;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bieres")
public class BiereController {
    private static Logger log = LoggerFactory.getLogger(BiereController.class);
    
    private final List<Biere> repositoryBiere;
    
    public BiereController() {
        repositoryBiere = new ArrayList<>();
        repositoryBiere.add(new Biere("Monalisa", 7.8));
        repositoryBiere.add(new Biere("Goudale", 5.3));
    }
    
    @GetMapping()
    public List<Biere> bieres() {
        log.info("Recuperation de la liste des biere pas {}", SecurityContextHolder.getContext().getAuthentication().getName());
        return repositoryBiere;
    }
    
    @PostMapping()
    public Biere newBiere(@RequestBody Biere newBiere) {
        repositoryBiere.add(newBiere);
        return newBiere;
    }
}
