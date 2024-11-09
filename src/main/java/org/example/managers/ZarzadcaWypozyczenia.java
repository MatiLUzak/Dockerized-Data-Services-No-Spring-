/*package org.example.managers;

import org.example.model.Wypozyczenie;
import org.example.repositories.RepozytoriumWypozyczenia;
import org.example.exceptions.RepozytoriumException;

public class ZarzadcaWypozyczenia {

    private final RepozytoriumWypozyczenia repozytorium;

    public ZarzadcaWypozyczenia(RepozytoriumWypozyczenia repozytorium) {
        this.repozytorium = repozytorium;
    }

    public void dodajWypozyczenie(Wypozyczenie wypozyczenie) {
        repozytorium.dodaj(wypozyczenie);
    }

    public void usunWypozyczenie(Wypozyczenie wypozyczenie) {
        if (wypozyczenie == null) {
            throw new RepozytoriumException("Nullptr exception");
        }
        wypozyczenie.koniecWypozyczenia();
        repozytorium.dodajDoHistoryczneWypoz(wypozyczenie);
        repozytorium.usun(wypozyczenie);
    }

    public double obliczKare(Wypozyczenie wypozyczenie) {
        var historyczneWypozyczenia = repozytorium.getHistoryczneWypoz();

        for (Wypozyczenie histWypozyczenie : historyczneWypozyczenia) {
            if (histWypozyczenie.equals(wypozyczenie)) {
                return histWypozyczenie.obliczKare();
            }
        }

        System.out.println("Wypożyczenie nie jest w historii wypożyczeń!");
        return -1;
    }
}*/
