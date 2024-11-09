/*package org.example.repositories;

import org.example.model.Wolumin;
import org.example.exceptions.RepozytoriumException;

import java.util.ArrayList;
import java.util.List;

public class RepozytoriumWoluminu extends Repozytorium<Wolumin> {

    private final List<Wolumin> historycznyWolumin = new ArrayList<>();

    public void dodajDoHistorycznych(Wolumin wolumin) {
        if (wolumin != null) {
            historycznyWolumin.add(wolumin);
        } else {
            throw new RepozytoriumException("Nie można dodać null do historycznych woluminów.");
        }
    }

    public List<Wolumin> getHistorycznyWolumin() {
        return new ArrayList<>(historycznyWolumin);
    }
}*/
