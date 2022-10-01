package ovh.ruokki.example.role.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ovh.ruokki.example.role.controller.dto.Biere;

@RestController
@RequestMapping("bieres")
public class BiereController {

    private final List<Biere> repositoryBiere;

    public BiereController() {
        repositoryBiere = new ArrayList<>();
        repositoryBiere.add(new Biere("Monalisa", 7.8));
        repositoryBiere.add(new Biere("Goudale", 5.3));
    }

    @GetMapping()
    public List<Biere> bieres() {
        return repositoryBiere;
    }

    @PostMapping()
    public Biere newBiere(@RequestBody Biere newBiere) {
         repositoryBiere.add(newBiere);
         return newBiere;
    }
}
