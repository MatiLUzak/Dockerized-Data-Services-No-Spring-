package org.example.model;

import org.example.exceptions.WoluminException;

public class Czasopismo extends Wolumin {
    private String nrWydania;

    public Czasopismo(String wydawnictwo, String jezyk, String tytul, String nrWydania) {
        super(wydawnictwo, jezyk, tytul);
        if(nrWydania==null||nrWydania.isEmpty())
        {
            throw new WoluminException("Brak numeru wydania");
        }
        this.nrWydania = nrWydania;
    }
    public Czasopismo() {}

    public String getNrWydania() {
        return nrWydania;
    }

    public void setNrWydania(String nrWydania) {
        if(nrWydania==null||nrWydania.isEmpty()){
            throw new WoluminException("Brak numeru wydania");
        }
        this.nrWydania = nrWydania;
    }
    public String pobierz_informacje() {
        StringBuilder info =new StringBuilder();
        info.append("Wydawnictwo: ").append(getWydawnictwo()).append("\n");
        info.append("Język: ").append(getJezyk()).append("\n");
        info.append("Tytuł: ").append(getTytul()).append("\n");
        info.append("Numer wydania: ").append(getNrWydania()).append("\n");
        return info.toString();
    }
}
