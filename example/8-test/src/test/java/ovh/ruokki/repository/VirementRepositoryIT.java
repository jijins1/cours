
package ovh.ruokki.repository;

import ovh.ruokki.domain.Compte;
import ovh.ruokki.domain.Virement;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class VirementRepositoryIT {

    @Autowired
    VirementRepository virementRepository;

    @Autowired
    CompteRepository compteRepository;

    @Test
    public void testSaveAndFindVirement() {
        var compteSource = new Compte();
        compteSource.setNumeroCompte("SourceTest");
        compteSource.setSolde(100.0);
        compteSource = compteRepository.save(compteSource);

        var compteCible = new Compte();
        compteCible.setNumeroCompte("CibleTest");
        compteCible.setSolde(50.0);
        compteCible = compteRepository.save(compteCible);

        var virement = new Virement();
        virement.setSource(compteSource);
        virement.setCible(compteCible);
        virement.setMontant(25.0);
        virement.setCreatedDate(LocalDateTime.now());
        virement.setUpdateDate(LocalDateTime.now().plusDays(1));
        virement = virementRepository.save(virement);

        var savedVirement = virementRepository.findById(virement.getId()).orElseThrow();

        Assertions.assertThat(savedVirement.getSource().getNumeroCompte()).isEqualTo("SourceTest");
        Assertions.assertThat(savedVirement.getCible().getNumeroCompte()).isEqualTo("CibleTest");
        Assertions.assertThat(savedVirement.getMontant()).isEqualTo(25.0);
        Assertions.assertThat(savedVirement.getUpdateDate()).isNotNull();
        Assertions.assertThat(savedVirement.getCreatedDate()).isNotNull();

    }
}
