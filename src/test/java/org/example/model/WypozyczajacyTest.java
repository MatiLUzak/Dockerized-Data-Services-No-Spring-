package org.example.model;

import org.example.exceptions.WypozyczajacyException;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WypozyczajacyTest {

    @Test
    void constructorTest() {
        // Tworzenie daty testowej (30 maja 2023)
        Date testDate = new Date(123, 4, 30); // Rok 2023: 123 = 2023 - 1900, Miesiące od 0, więc maj to 4
        TypWypozyczajacy uczen = new Uczen(0.0, 30, 10, "2");

        Wypozyczajacy w1 = new Wypozyczajacy(uczen, "Jan Kowalski", testDate, "Kraków");

        assertEquals(10, w1.getTypWypozyczajacy().getMaksLKsiazek());

        w1.setNazwa("Marian Las");
        w1.getTypWypozyczajacy().setMaxDlWypoz(5);

        assertEquals("Marian Las", w1.getNazwa());
        assertEquals(5, w1.getTypWypozyczajacy().getMaxDlWypoz());
        // assertNotNull(w1.getUuid());  // Sprawdzenie, czy UUID nie jest null
        assertEquals(testDate, w1.getDataUr());
    }

    @Test
    void setterExceptionTest() {
        // Tworzenie daty testowej (30 maja 2023)
        Date testDate = new Date(123, 4, 30); // Rok 2023: 123 = 2023 - 1900, Miesiące od 0, więc maj to 4
        TypWypozyczajacy uczen = new Uczen(0.0, 30, 10, "2");
        Wypozyczajacy wypozycajacy = new Wypozyczajacy(uczen, "Jan Kowalski", testDate, "Kraków");

        // Sprawdzamy, czy wyjątki są rzucane prawidłowo
        assertThrows(WypozyczajacyException.class, () -> wypozycajacy.setTypWypozyczajacy(null));
        assertThrows(WypozyczajacyException.class, () -> wypozycajacy.setNazwa(""));
        assertThrows(WypozyczajacyException.class, () -> wypozycajacy.setAdres(""));
    }
}
