package ovh.ruokki.example.openid.controller.dto;

public class UtilisateurDto {
    
    public UtilisateurDto(final String prenom) {
        this.prenom = prenom;
    }
    
    private String prenom;
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(final String prenom) {
        this.prenom = prenom;
    }
}
