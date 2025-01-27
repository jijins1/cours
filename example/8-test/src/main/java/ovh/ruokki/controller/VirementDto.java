package ovh.ruokki.controller;


public class VirementDto {
    private String source;
    private String destination;
    private Integer montant;
    
    public String getSource() {
        return source;
    }
    
    public void setSource(final String source) {
        this.source = source;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(final String destination) {
        this.destination = destination;
    }
    
    public Integer getMontant() {
        return montant;
    }
    
    public void setMontant(final Integer montant) {
        this.montant = montant;
    }
    
}
