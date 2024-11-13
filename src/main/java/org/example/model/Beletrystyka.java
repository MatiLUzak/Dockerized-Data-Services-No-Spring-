package org.example.model;

import org.example.exceptions.WoluminException;

import java.util.List;

public class Beletrystyka extends Ksiazka{
    private String przedziałWiekowy;
    private String rodzaj;

    public Beletrystyka() {}

    public Beletrystyka(String wydawnictwo, String jezyk, String tytul, List<String> autor, String przedział_wiekowy, String rodzaj) {
        super(wydawnictwo, jezyk, tytul, autor);
        if(przedział_wiekowy==null||przedział_wiekowy.isEmpty())
        {
            throw new WoluminException("Przedzial wiekowy nie moze byc pusty");
        }
        if(rodzaj==null||rodzaj.isEmpty())
        {
            throw new WoluminException("Rodzaj nie moze byc pusty");
        }
        this.przedziałWiekowy = przedział_wiekowy;

        this.rodzaj = rodzaj;
    }

    public String getPrzedział_wiekowy() {
        return przedziałWiekowy;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setPrzedział_wiekowy(String przedział_wiekowy) {
        if(przedział_wiekowy==null||przedział_wiekowy.isEmpty()){
            throw new WoluminException("Przedzial wiekowy nie moze byc pusty");
        }
        this.przedziałWiekowy = przedział_wiekowy;
    }

    public void setRodzaj(String rodzaj) {
        if(rodzaj==null||rodzaj.isEmpty())
        {
            throw new WoluminException("Rodzaj nie moze byc pusty");
        }
        this.rodzaj = rodzaj;
    }
    public String pobierz_informacje() {
        StringBuilder info =new StringBuilder();
        info.append("Wydawnictwo: ").append(getWydawnictwo()).append("\n");
        info.append("Język: ").append(getJezyk()).append("\n");
        info.append("Tytuł: ").append(getTytul()).append("\n");
        info.append("Przedział wiekowy: ").append(getPrzedział_wiekowy()).append("\n");
        info.append("Rodzaj: ").append(getRodzaj()).append("\n");
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
