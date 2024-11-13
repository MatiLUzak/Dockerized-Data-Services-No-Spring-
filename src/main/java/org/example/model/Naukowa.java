package org.example.model;

import org.example.exceptions.WoluminException;

import java.util.List;

public class Naukowa extends Ksiazka{
    private String recenzja;
    private String dział;

    public Naukowa(String wydawnictwo, String jezyk, String tytul, List<String> autor, String recenzja, String dział) {
        super(wydawnictwo, jezyk, tytul, autor);
        if(recenzja==null||recenzja.isEmpty()){
            throw  new WoluminException("Recenzja nie moze byc pusta");
        }
        if(dział==null||dział.isEmpty()){
            throw new WoluminException("Dzial nie moze byc pusty");
        }
        this.recenzja = recenzja;
        this.dział = dział;

    }
    public Naukowa(){}

    public String getRecenzja() {
        return recenzja;
    }

    public String getDział() {
        return dział;
    }

    public void setRecenzja(String recenzja) {
        if(recenzja==null||recenzja.isEmpty()){
            throw new WoluminException("Recenzja nie moze byc pusta");
        }
        this.recenzja = recenzja;
    }

    public void setDział(String dział) {
        if(dział==null||dział.isEmpty()){
            throw new WoluminException("Dzial nie moze byc pusty");
        }
        this.dział = dział;
    }
    public String pobierz_informacje() {
        StringBuilder info =new StringBuilder();
        info.append("Wydawnictwo: ").append(getWydawnictwo()).append("\n");
        info.append("Język: ").append(getJezyk()).append("\n");
        info.append("Tytuł: ").append(getTytul()).append("\n");
        info.append("recenzja: ").append(getRecenzja()).append("\n");
        info.append("dział: ").append(getDział()).append("\n");
        info.append("Autorzy: ");
        List<String> autorzy = getAutor();
        for (int i = 0; i < autorzy.size(); i++) {
            info.append(autorzy.get(i));
            if (i < autorzy.size() - 1) {
                info.append(", ");
            }
        }
        info.append("\n");
        return info.toString();
    }
}
