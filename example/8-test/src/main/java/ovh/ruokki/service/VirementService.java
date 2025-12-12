package ovh.ruokki.service;

import org.springframework.stereotype.Service;
import ovh.ruokki.domain.Compte;
import ovh.ruokki.domain.Virement;
import ovh.ruokki.repository.VirementRepository;
import ovh.ruokki.service.CompteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VirementService {

    private static final Logger log = LoggerFactory.getLogger(VirementService.class);


    private final CompteService compteService;
    private final VirementRepository virementRepository;

    public VirementService(final CompteService compteService, VirementRepository virementRepository) {
        this.compteService = compteService;
        this.virementRepository = virementRepository;
    }

    /**
     * Effectue un virement en le compte source et le compte cible
     *
     * @param source  numero de compte source
     * @param cible   numeor de compte cible
     * @param montant a virer
     * @return true si le virement est effectué
     */
    public boolean applyVirement(final String source, final String cible, final double montant) {
        log.info("Application d'un virement d'un montant de {} de {} à {} ", montant, source, cible);

        Compte compteSource = compteService.getCompte(source);
        Compte compteCible = compteService.getCompte(cible);

        compteService.updateMontant(source, -montant);
        compteService.updateMontant(cible, montant);

        Virement virement = new Virement();
        virement.setSource(compteSource);
        virement.setCible(compteCible);
        virement.setMontant(montant);
        this.virementRepository.save(virement);
        log.info("Virement enregistré");

        return true;
    }
}
