package ovh.ruokki.example.basic.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "UTILISATEUR")
public class Utilisateur {
    @Id
    @GeneratedValue
    private Long id;
    
    private String login;
    
    private String password;
    
    public Long getId() {
        return id;
    }
    
    public void setId(final Long id) {
        this.id = id;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(final String email) {
        this.login = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
}
