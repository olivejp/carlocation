package nc.olive.carlocation.domain;

import java.time.LocalDate;
import java.util.List;

public class LocationDto {

    private String immatriculation;
    private Integer kilometrage;
    private String nomEmprunteur;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private List<String> listDefaults;

    public String getImmatriculation() {
        return immatriculation;
    }

    public Integer getKilometrage() {
        return kilometrage;
    }

    public String getNomEmprunteur() {
        return nomEmprunteur;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public List<String> getListDefaults() {
        return listDefaults;
    }

    @Override
    public String toString() {
        return "LocationDto{" +
                "immatriculation='" + immatriculation + '\'' +
                ", kilometrage=" + kilometrage +
                ", nomEmprunteur='" + nomEmprunteur + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", listDefaults=" + listDefaults +
                '}';
    }
}
