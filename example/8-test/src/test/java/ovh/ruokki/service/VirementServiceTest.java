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
    public void itShouldValidatVirement() {
        //given
        Compte source = new Compte();
        Compte destination = new Compte();
        source.setSolde(100);
        destination.setSolde(122);
        source.setNumeroCompte("source");
        destination.setNumeroCompte("destination");
        Mockito.doReturn(Optional.of(source)).when(compteRepository).findById("source");
        Mockito.doReturn(Optional.of(destination)).when(compteRepository).findById("destination");
        
        
        //when
        boolean isValid = virementService.validationVirement("source","destination", 80);
        //then
        Assertions.assertThat(isValid).isTrue();
        Assertions.assertThat(destination.getSolde()).isEqualTo(202);
        Assertions.assertThat(source.getSolde()).isEqualTo(20);
    }
    @Test
    public void itShouldThrowErrorIfNotSource() {
        
        Mockito.doReturn(Optional.empty()).when(compteRepository).findById("source");
        
        
        
        //when
        Assertions.assertThatThrownBy( ()->virementService.validationVirement("source","destination", 80))
                .isInstanceOf(RuntimeException.class);
        //then
    }
    @Test
    public void soldIsEnough() {
        //given
        Compte source = new Compte();
        Compte destination = new Compte();
        source.setSolde(100);
        destination.setSolde(122);
        source.setNumeroCompte("source");
        destination.setNumeroCompte("destination");
        Mockito.doReturn(Optional.of(source)).when(compteRepository).findById("source");
        Mockito.doReturn(Optional.of(destination)).when(compteRepository).findById("destination");
        
        
        //when
        boolean isValid = virementService.validationVirement("source","destination", 404);
        //then
        Assertions.assertThat(isValid).isFalse();
        Assertions.assertThat(destination.getSolde()).isEqualTo(122);
        Assertions.assertThat(source.getSolde()).isEqualTo(100);
    }

}