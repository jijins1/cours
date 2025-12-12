package ovh.ruokki.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ovh.ruokki.domain.Compte;
import ovh.ruokki.repository.VirementRepository;

class VirementServiceTest {

    VirementService virementService;

    CompteService compteService;
    VirementRepository virementRepository;

    @BeforeEach
    void setUp() {
        compteService = Mockito.mock(CompteService.class);
        virementRepository = Mockito.mock(VirementRepository.class);
        virementService = new VirementService(compteService, virementRepository);
    }

    @Test
    public void test1() {

        var compteSource = new Compte();
        var compteCible = new Compte();

        compteSource.setNumeroCompte("Source");
        compteCible.setNumeroCompte("Cible");
        compteSource.setSolde(10);
        compteCible.setSolde(0);
        Mockito.when(compteService.getCompte("Source")).thenReturn(compteSource);
        Mockito.when(compteService.getCompte("Cible")).thenReturn(compteCible);
        var result = virementService.applyVirement("Source", "Cible", 1);

        Assertions.assertThat(result).isTrue();

        // Verify that updateMontant was called
        Mockito.verify(compteService).updateMontant("Source", -1.0);
        Mockito.verify(compteService).updateMontant("Cible", 1.0);
        // Verify virement was saved
        Mockito.verify(virementRepository).save(Mockito.any());
    }

}
