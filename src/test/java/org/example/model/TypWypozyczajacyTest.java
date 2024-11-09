package org.example.model;

import org.example.exceptions.WypozyczajacyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypWypozyczajacyTest {

    @Test
    void uczenCase() {
        Uczen uczen = new Uczen(1.0, 14, 5, "1");

        uczen.setKara(1.5);
        uczen.setMaxDlWypoz(16);
        uczen.setMaksLKsiazek(6);
        uczen.setNrSemestru("2");

        assertEquals(1.5, uczen.getKara());
        assertEquals(16, uczen.getMaxDlWypoz());
        assertEquals(6, uczen.getMaksLKsiazek());
        assertEquals("2", uczen.getNrSemestru());
    }

    @Test
    void uczenExceptionCase() {
        assertThrows(WypozyczajacyException.class, () -> new Uczen(1.0, -1, 5, "1"));
        assertThrows(WypozyczajacyException.class, () -> new Uczen(1.0, 14, -1, "1"));
        assertThrows(WypozyczajacyException.class, () -> new Uczen(-1.0, 14, 5, "1"));
        assertThrows(WypozyczajacyException.class, () -> new Uczen(1.0, 14, 5, ""));

        Uczen uczen = new Uczen(1.0, 14, 5, "1");

        assertThrows(WypozyczajacyException.class, () -> uczen.setKara(-1.0));
        assertThrows(WypozyczajacyException.class, () -> uczen.setMaxDlWypoz(-1));
        assertThrows(WypozyczajacyException.class, () -> uczen.setMaksLKsiazek(-1));
        assertThrows(WypozyczajacyException.class, () -> uczen.setNrSemestru(""));
    }

    @Test
    void nauczycielCase() {
        Nauczyciel nauczyciel = new Nauczyciel(2.0, 30, 10, "dr");

        nauczyciel.setKara(2.5);
        nauczyciel.setMaxDlWypoz(32);
        nauczyciel.setMaksLKsiazek(11);
        nauczyciel.setTytul("prof.");

        assertEquals(2.5, nauczyciel.getKara());
        assertEquals(32, nauczyciel.getMaxDlWypoz());
        assertEquals(11, nauczyciel.getMaksLKsiazek());
        assertEquals("prof.", nauczyciel.getTytul());
    }

    @Test
    void nauczycielExceptionCase() {
        assertThrows(WypozyczajacyException.class, () -> new Nauczyciel(2.0, -1, 10, "dr"));
        assertThrows(WypozyczajacyException.class, () -> new Nauczyciel(2.0, 30, -1, "dr"));
        assertThrows(WypozyczajacyException.class, () -> new Nauczyciel(-2.0, 30, 10, "dr"));
        assertThrows(WypozyczajacyException.class, () -> new Nauczyciel(2.0, 30, 10, ""));

        Nauczyciel nauczyciel = new Nauczyciel(2.0, 30, 10, "dr");

        assertThrows(WypozyczajacyException.class, () -> nauczyciel.setKara(-2.0));
        assertThrows(WypozyczajacyException.class, () -> nauczyciel.setMaxDlWypoz(-1));
        assertThrows(WypozyczajacyException.class, () -> nauczyciel.setMaksLKsiazek(-1));
        assertThrows(WypozyczajacyException.class, () -> nauczyciel.setTytul(""));
    }

    @Test
    void pozostaliCase() {
        Pozostali pozostali = new Pozostali(1.5, 21, 7, "Inzynier");

        pozostali.setKara(2.0);
        pozostali.setMaxDlWypoz(23);
        pozostali.setMaksLKsiazek(8);
        pozostali.setZawod("Programista");

        assertEquals(2.0, pozostali.getKara());
        assertEquals(23, pozostali.getMaxDlWypoz());
        assertEquals(8, pozostali.getMaksLKsiazek());
        assertEquals("Programista", pozostali.getZawod());
    }

    @Test
    void pozostaliExceptionCase() {
        assertThrows(WypozyczajacyException.class, () -> new Pozostali(1.5, -1, 7, "Inzynier"));
        assertThrows(WypozyczajacyException.class, () -> new Pozostali(1.5, 21, -1, "Inzynier"));
        assertThrows(WypozyczajacyException.class, () -> new Pozostali(-1.5, 21, 7, "Inzynier"));
        assertThrows(WypozyczajacyException.class, () -> new Pozostali(1.5, 21, 7, ""));

        Pozostali pozostali = new Pozostali(1.5, 21, 7, "Inzynier");

        assertThrows(WypozyczajacyException.class, () -> pozostali.setKara(-2.0));
        assertThrows(WypozyczajacyException.class, () -> pozostali.setMaxDlWypoz(-1));
        assertThrows(WypozyczajacyException.class, () -> pozostali.setMaksLKsiazek(-1));
        assertThrows(WypozyczajacyException.class, () -> pozostali.setZawod(""));
    }
}
