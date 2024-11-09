/*package org.example.repositories;

import org.example.model.Beletrystyka;
import org.example.model.Wolumin;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepozytoriumWoluminuTest {

    @Test
    void testDodajDoHistorycznych() {
        RepozytoriumWoluminu repo = new RepozytoriumWoluminu();

        List<String> autorzy = Arrays.asList("J.K. Rowling");
        Wolumin wolumin1 = new Beletrystyka("Helion", "PL", "Harry Potter", autorzy, "15+", "Fantasy");

        repo.dodajDoHistorycznych(wolumin1);

        assertEquals(1, repo.getHistorycznyWolumin().size());
    }
}*/
