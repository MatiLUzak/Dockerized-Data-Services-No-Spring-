package org.example.model;

import org.example.exceptions.WypozyczajacyException;

public class Nauczyciel extends TypWypozyczajacy {
    private String tytul;

    public Nauczyciel(double kara, int maxDlWypoz, int maksLKsiazek, String tytul) {
        super(kara, maxDlWypoz, maksLKsiazek);
        if (tytul == null || tytul.isEmpty()) {
            throw new WypozyczajacyException("Błędny tytuł");
        }
        this.tytul = tytul;
    }
    public Nauczyciel() {}

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        if (tytul == null || tytul.isEmpty()) {
            throw new WypozyczajacyException("Błędny tytuł");
        }
        this.tytul = tytul;
    }

    public String pobierzInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Kara: ").append(getKara()).append("\n");
        info.append("Max długość wypożyczenia: ").append(getMaxDlWypoz()).append("\n");
        info.append("Maksymalna liczba książek: ").append(getMaksLKsiazek()).append("\n");
        info.append("Tytuł: ").append(getTytul()).append("\n");
        return info.toString();
    }
}
