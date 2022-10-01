package ovh.ruokki.example.role.controller.dto;

public class Biere {

    private String nom;
    private double degres;

    public Biere(String nom, double degres) {
        this.nom = nom;
        this.degres = degres;
    }

    public double getDegres() {
        return degres;
    }

    public void setDegres(double degres) {
        this.degres = degres;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}