/*package org.example.managers;

import org.example.exceptions.RepozytoriumException;
import org.example.model.Naukowa;
import org.example.model.Wolumin;
import org.example.repositories.RepozytoriumWoluminu;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class ZarzadcaWoluminuTest {

    @Test
    void dodajUsunWoluminTest() {
        RepozytoriumWoluminu repozytoriumWoluminu = new RepozytoriumWoluminu();
        ZarzadcaWoluminu zarzadca = new ZarzadcaWoluminu(repozytoriumWoluminu);

        List<String> autorzy = Arrays.asList("Autor1", "Autor2");
        Wolumin n1 = new Naukowa("Wydawnictwo1", "Polski", "Tytuł1", autorzy, "Recenzja1", "Dział1");

        zarzadca.dodajWolumin(n1);
        assertEquals(1, repozytoriumWoluminu.znajdz(w -> true).size());

        zarzadca.usunWolumin(n1);
        assertEquals(0, repozytoriumWoluminu.znajdz(w -> true).size());
    }

    @Test
    void znajdzWoluminyTest() {
        RepozytoriumWoluminu repozytoriumWoluminu = new RepozytoriumWoluminu();
        ZarzadcaWoluminu zarzadca = new ZarzadcaWoluminu(repozytoriumWoluminu);

        List<String> autorzy = Arrays.asList("Autor1", "Autor2");
        Wolumin n1 = new Naukowa("Wydawnictwo1", "Polski", "Tytuł1", autorzy, "Recenzja1", "Dział1");
        Wolumin n2 = new Naukowa("Wydawnictwo2", "Polski", "Tytuł2", autorzy, "Recenzja2", "Dział2");

        zarzadca.dodajWolumin(n1);
        zarzadca.dodajWolumin(n2);

        List<Wolumin> wynik = repozytoriumWoluminu.znajdz(w -> true);
        assertEquals(2, wynik.size());

        String specificPublisher = "Wydawnictwo1";
        Wolumin wolumin2 = new Naukowa("Wydawnictwo2", "Angielski", "Tytuł2", Arrays.asList("Autor3", "Autor4"), "Recenzja2", "Dział2");
        zarzadca.dodajWolumin(wolumin2);

        List<Wolumin> foundVolumes = repozytoriumWoluminu.znajdz(w -> w.getWydawnictwo().equals(specificPublisher));
        assertEquals(1, foundVolumes.size());
        assertEquals(specificPublisher, foundVolumes.get(0).getWydawnictwo());
    }

    @Test
    void exceptionTest() {
        RepozytoriumWoluminu repozytoriumWoluminu = new RepozytoriumWoluminu();
        ZarzadcaWoluminu zarzadca = new ZarzadcaWoluminu(repozytoriumWoluminu);

        assertThrows(RepozytoriumException.class, () -> zarzadca.dodajWolumin(null));
        assertThrows(RepozytoriumException.class, () -> zarzadca.usunWolumin(null));
    }
}*/
