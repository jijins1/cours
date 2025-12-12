package ovh.ruokki.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ovh.ruokki.domain.Compte;
import ovh.ruokki.repository.CompteRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CompteService {

    private static final Logger log = LoggerFactory.getLogger(CompteService.class);
    private final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    /**
     * Crée un nouveau compte avec son numéro et solde initial
     * @param numeroCompte le numéro du compte
     * @param initialSolde le solde initial
     * @return le compte créé et sauvé
     */
    public Compte createCompte(String numeroCompte, double initialSolde) {
        Compte compte = new Compte();
        compte.setNumeroCompte(numeroCompte);
        compte.setSolde(initialSolde);
        compte.setCreatedDate(LocalDateTime.now());
        compte.setUpdateDate(LocalDateTime.now());
        return compteRepository.save(compte);
    }

    /**
     * Met à jour le solde d'un compte existant en ajoutant le montant
     * @param numeroCompte le numéro du compte
     * @param montant le montant à ajouter (positif pour crédit, négatif pour débit)
     * @return le compte mis à jour
     */
    public Compte updateMontant(String numeroCompte, double montant) {
        log.info("Mise à jour du montant pour le compte {}", numeroCompte);
        Compte compte = compteRepository.findById(numeroCompte).orElseThrow();
        compte.setSolde(compte.getSolde() + montant);
        compte.setUpdateDate(LocalDateTime.now());
        return compteRepository.save(compte);
    }

    /**
     * Récupère un compte par son numéro
     * @param numeroCompte le numéro du compte
     * @return le compte trouvé
     */
    public Compte getCompte(String numeroCompte) {
        return compteRepository.findById(numeroCompte).orElseThrow();
    }

    /**
     * Sauvegarde un compte POJO classique
     * @param compte le compte à sauvegarder
     * @return le compte sauvegardé
     */
    public Compte saveCompte(Compte compte) {
        return compteRepository.save(compte);
    }
}
