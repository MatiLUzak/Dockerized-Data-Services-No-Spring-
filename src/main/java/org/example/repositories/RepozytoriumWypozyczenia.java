/*package org.example.repositories;

import org.example.model.Wypozyczenie;
import org.example.exceptions.RepozytoriumException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RepozytoriumWypozyczenia extends Repozytorium<Wypozyczenie> {

    private final List<Wypozyczenie> historyczneWypozyczenia = new ArrayList<>();

    public Wypozyczenie znajdzPoIdWypoz(UUID id) {
        return znajdz(m -> m.getUuid().equals(id))
                .stream().findFirst().orElse(null);
    }

    public void dodajDoHistoryczneWypoz(Wypozyczenie wypozyczenie) {
        if (wypozyczenie != null) {
            historyczneWypozyczenia.add(wypozyczenie);
        } else {
            throw new RepozytoriumException("Nie można dodać null do historycznych wypożyczeń.");
        }
    }

    public List<Wypozyczenie> getHistoryczneWypoz() {
        return new ArrayList<>(historyczneWypozyczenia);
    }
}*/
