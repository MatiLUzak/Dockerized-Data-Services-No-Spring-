/*package org.example.repositories;

import org.example.model.Wypozyczajacy;
import org.example.exceptions.RepozytoriumException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RepozytoriumWypozyczajacy extends Repozytorium<Wypozyczajacy> {

    private final List<Wypozyczajacy> historyczniWypozyczajacy = new ArrayList<>();

    public Wypozyczajacy znajdzPoIdWypozyc(UUID id) {
        return znajdz(m -> m.getUuid().equals(id))
                .stream().findFirst().orElse(null);
    }

    public void dodajDoHistoryczni(Wypozyczajacy wypozyczajacy) {
        if (wypozyczajacy != null) {
            historyczniWypozyczajacy.add(wypozyczajacy);
        } else {
            throw new RepozytoriumException("Nie można dodać null do historycznych wypożyczających.");
        }
    }

    public List<Wypozyczajacy> getHistoryczniWypozyczajacy() {
        return new ArrayList<>(historyczniWypozyczajacy);
    }
}*/
