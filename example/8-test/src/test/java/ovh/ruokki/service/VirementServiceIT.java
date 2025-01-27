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
        //given
        Compte source = new Compte();
        Compte destination = new Compte();
        source.setSolde(100);
        destination.setSolde(122);
        source.setNumeroCompte("source");
        destination.setNumeroCompte("destination");
        compteRepository.save(source);
        compteRepository.save(destination);
        //when
        boolean isValid = virementService.validationVirement("source","destination", 80);
        //then
        Optional<Compte> sourceBase = compteRepository.findById("source");
        Optional<Compte> destinationBase = compteRepository.findById("destination");
        Assertions.assertThat(destinationBase.get().getSolde()).isEqualTo(202);
        Assertions.assertThat(sourceBase.get().getSolde()).isEqualTo(20);
        
    }

}