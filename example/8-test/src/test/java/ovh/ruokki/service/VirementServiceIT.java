package ovh.ruokki.service;

import java.util.Optional;

import ovh.ruokki.domain.Compte;
import ovh.ruokki.repository.CompteRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VirementServiceIT {
    
    @Autowired
    VirementService virementService;
    
    @Autowired
    CompteRepository compteRepository;
    
    
    @Test
    public void test1() {
        
        var compteSource = new Compte() ;
        var compteCible = new Compte();
        
        compteSource.setNumeroCompte("Source");
        compteCible.setNumeroCompte("Cible");
        compteSource.setSolde(10);
        compteCible.setSolde(0);
        
        this.compteRepository.save(compteCible);
        this.compteRepository.save(compteSource);

        var result = virementService.validationVirement("Source","Cible",1);
        
        
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(this.compteRepository.findById("Source").get().getSolde()).isEqualTo(9);
        Assertions.assertThat(this.compteRepository.findById("Cible").get().getSolde()).isEqualTo(1);
        
    }

}