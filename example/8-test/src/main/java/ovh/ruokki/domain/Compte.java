package ovh.ruokki.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="compte")
public class Compte {
    @Id
    private String numeroCompte;
    
    private double solde;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
