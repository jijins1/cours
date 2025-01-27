package ovh.ruokki.service;

import java.util.function.Consumer;

import ovh.ruokki.domain.Compte;
import ovh.ruokki.repository.CompteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VirementService {
    
    
    private final CompteRepository compteRepository;
    
    public VirementService(final CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }
    
    public boolean validationVirement(final String source, final String destination, final double montant) {
        Compte compteSource = compteRepository.findById(source).orElseThrow(() -> new RuntimeException());
        Compte compteDestination = compteRepository.findById(destination).orElseThrow(() -> new RuntimeException());
        
        if(montant >= 2 * compteSource.getSolde())  return false;
        
        compteSource.setSolde(compteSource.getSolde()-montant);
        compteDestination.setSolde(compteDestination.getSolde()+montant);
        
        compteRepository.save(compteSource);
        compteRepository.save(compteDestination);
        return true;
    }
}
