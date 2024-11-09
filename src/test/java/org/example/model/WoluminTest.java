package org.example.model;

import org.example.exceptions.WoluminException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WoluminTest {

    @Test
    void testNaukowa() {
        List<String> autors = Arrays.asList("Autor1", "Autor2");
        Naukowa n1 = new Naukowa("Wydawnictwo1", "Polski", "Tytuł1", autors, "Recenzja1", "Dział1");

        n1.setWydawnictwo("Wydawnictwo2");
        n1.setJezyk("Angielski");
        n1.setTytul("Tytuł2");
        n1.setRecenzja("Recenzja2");
        n1.setDział("Dział2");
        List<String> newAutors = Arrays.asList("Autor3", "Autor4");
        n1.setAutor(newAutors);

        assertEquals("Wydawnictwo2", n1.getWydawnictwo());
        assertEquals("Angielski", n1.getJezyk());
        assertEquals("Tytuł2", n1.getTytul());
        assertEquals("Recenzja2", n1.getRecenzja());
        assertEquals("Dział2", n1.getDział());
        assertEquals(newAutors, n1.getAutor());
    }

    @Test
    void testNaukowaException() {
        List<String> autors = Arrays.asList("Autor1", "Autor2");
        List<String> emptyAutors = Collections.emptyList();

        assertThrows(WoluminException.class, () -> new Naukowa("", "Polski", "Tytuł1", autors, "Recenzja1", "Dział1"));
        assertThrows(WoluminException.class, () -> new Naukowa("Wydawnictwo1", "", "Tytuł1", autors, "Recenzja1", "Dział1"));
        assertThrows(WoluminException.class, () -> new Naukowa("Wydawnictwo1", "Polski", "", autors, "Recenzja1", "Dział1"));
        assertThrows(WoluminException.class, () -> new Naukowa("Wydawnictwo1", "Polski", "Tytuł1", autors, "", "Dział1"));
        assertThrows(WoluminException.class, () -> new Naukowa("Wydawnictwo1", "Polski", "Tytuł1", autors, "Recenzja1", ""));
        assertThrows(WoluminException.class, () -> new Naukowa("Wydawnictwo1", "Polski", "Tytuł1", emptyAutors, "Recenzja1", "Dział1"));

        Naukowa n1 = new Naukowa("Wydawnictwo1", "Polski", "Tytuł1", autors, "Recenzja1", "Dział1");

        assertThrows(WoluminException.class, () -> n1.setWydawnictwo(""));
        assertThrows(WoluminException.class, () -> n1.setJezyk(""));
        assertThrows(WoluminException.class, () -> n1.setTytul(""));
        assertThrows(WoluminException.class, () -> n1.setRecenzja(""));
        assertThrows(WoluminException.class, () -> n1.setDział(""));
        assertThrows(WoluminException.class, () -> n1.setAutor(emptyAutors));
    }

    @Test
    void testCzasopismo() {
        Czasopismo c1 = new Czasopismo("Wydawnictwo1", "Polski", "Tytuł1", "Nr1");
        c1.setWydawnictwo("Wydawnictwo2");
        c1.setJezyk("Angielski");
        c1.setTytul("Tytuł2");
        c1.setNrWydania("Nr2");

        assertEquals("Wydawnictwo2", c1.getWydawnictwo());
        assertEquals("Angielski", c1.getJezyk());
        assertEquals("Tytuł2", c1.getTytul());
        assertEquals("Nr2", c1.getNrWydania());
    }

    @Test
    void testCzasopismoException() {
        assertThrows(WoluminException.class, () -> new Czasopismo("", "Polski", "Tytuł1", "Nr1"));
        assertThrows(WoluminException.class, () -> new Czasopismo("Wydawnictwo1", "", "Tytuł1", "Nr1"));
        assertThrows(WoluminException.class, () -> new Czasopismo("Wydawnictwo1", "Polski", "", "Nr1"));
        assertThrows(WoluminException.class, () -> new Czasopismo("Wydawnictwo1", "Polski", "Tytuł1", ""));

        Czasopismo c1 = new Czasopismo("Wydawnictwo1", "Polski", "Tytuł1", "Nr1");

        assertThrows(WoluminException.class, () -> c1.setWydawnictwo(""));
        assertThrows(WoluminException.class, () -> c1.setJezyk(""));
        assertThrows(WoluminException.class, () -> c1.setTytul(""));
        assertThrows(WoluminException.class, () -> c1.setNrWydania(""));
    }

    @Test
    void testBeletrystyka() {
        List<String> autors = Arrays.asList("Autor1", "Autor2");
        Beletrystyka b1 = new Beletrystyka("Wydawnictwo1", "Polski", "Tytuł1", autors, "Przedział1", "Rodzaj1");

        b1.setWydawnictwo("Wydawnictwo2");
        b1.setJezyk("Angielski");
        b1.setTytul("Tytuł2");
        b1.setPrzedział_wiekowy("Przedział2");
        b1.setRodzaj("Rodzaj2");
        List<String> newAutors = Arrays.asList("Autor3", "Autor4");
        b1.setAutor(newAutors);

        assertEquals("Wydawnictwo2", b1.getWydawnictwo());
        assertEquals("Angielski", b1.getJezyk());
        assertEquals("Tytuł2", b1.getTytul());
        assertEquals("Przedział2", b1.getPrzedział_wiekowy());
        assertEquals("Rodzaj2", b1.getRodzaj());
        assertEquals(newAutors, b1.getAutor());
    }

    @Test
    void testBeletrystykaException() {
        List<String> autors = Arrays.asList("Autor1", "Autor2");
        List<String> emptyAutors = Collections.emptyList();

        assertThrows(WoluminException.class, () -> new Beletrystyka("", "Polski", "Tytuł1", autors, "Przedział1", "Rodzaj1"));
        assertThrows(WoluminException.class, () -> new Beletrystyka("Wydawnictwo1", "", "Tytuł1", autors, "Przedział1", "Rodzaj1"));
        assertThrows(WoluminException.class, () -> new Beletrystyka("Wydawnictwo1", "Polski", "", autors, "Przedział1", "Rodzaj1"));
        assertThrows(WoluminException.class, () -> new Beletrystyka("Wydawnictwo1", "Polski", "Tytuł1", autors, "", "Rodzaj1"));
        assertThrows(WoluminException.class, () -> new Beletrystyka("Wydawnictwo1", "Polski", "Tytuł1", autors, "Przedział1", ""));
        assertThrows(WoluminException.class, () -> new Beletrystyka("Wydawnictwo1", "Polski", "Tytuł1", emptyAutors, "Przedział1", "Rodzaj1"));

        Beletrystyka b1 = new Beletrystyka("Wydawnictwo1", "Polski", "Tytuł1", autors, "Przedział1", "Rodzaj1");

        assertThrows(WoluminException.class, () -> b1.setWydawnictwo(""));
        assertThrows(WoluminException.class, () -> b1.setJezyk(""));
        assertThrows(WoluminException.class, () -> b1.setTytul(""));
        assertThrows(WoluminException.class, () -> b1.setPrzedział_wiekowy(""));
        assertThrows(WoluminException.class, () -> b1.setRodzaj(""));
        assertThrows(WoluminException.class, () -> b1.setAutor(emptyAutors));
    }
}
