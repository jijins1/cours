package ovh.ruokki.service;

import ovh.ruokki.domain.Compte;
import ovh.ruokki.repository.CompteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VirementService {
    
    
    CompteRepository compteRepository;
    
    public VirementService(final CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }
    
    public boolean validationVirement(final String source, final String cible, final double i) {
        Compte compteSource = compteRepository.findById(source).get();
        Compte compteCible = compteRepository.findById(cible).get();
        compteSource.setSolde(compteSource.getSolde()-i);
        compteCible.setSolde(compteCible.getSolde()+i);
        
        this.compteRepository.save(compteCible);
        this.compteRepository.save(compteSource);
        
        return true;
    }
}
