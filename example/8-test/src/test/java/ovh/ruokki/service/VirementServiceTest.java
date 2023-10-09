package ovh.ruokki.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import ovh.ruokki.domain.Compte;
import ovh.ruokki.repository.CompteRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VirementServiceTest {
    
    VirementService virementService;
    
    CompteRepository compteRepository;
    
    @BeforeEach
    void setUp() {
        compteRepository = Mockito.mock(CompteRepository.class);
        virementService = new VirementService(compteRepository);
    }
    
    @Test
    public void test1() {
        
        var compteSource = new Compte() ;
        var compteCible = new Compte();
        
        compteSource.setNumeroCompte("Source");
        compteCible.setNumeroCompte("Cible");
        compteSource.setSolde(10);
        compteCible.setSolde(0);
        Mockito.doReturn(Optional.of(compteCible)).when(compteRepository).findById("Cible");
        Mockito.doReturn(Optional.of(compteSource)).when(compteRepository).findById("Source");
        var result = virementService.validationVirement("Source","Cible",1);
        
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(compteSource.getSolde()).isEqualTo(9);
        Assertions.assertThat(compteCible.getSolde()).isEqualTo(1);
        
    }

}