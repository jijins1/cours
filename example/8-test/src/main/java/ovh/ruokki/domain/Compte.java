package ovh.ruokki.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="compte")
public class Compte {
    @Id
    private String numeroCompte;
    
    private double solde;
    
    public double getSolde() {
        return solde;
    }
    
    public String getNumeroCompte() {
        return numeroCompte;
    }
    
    public void setNumeroCompte(final String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }
    
    public void setSolde(final double solde) {
        this.solde = solde;
    }
}
