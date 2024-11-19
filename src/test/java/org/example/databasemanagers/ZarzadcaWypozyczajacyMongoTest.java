package org.example.databasemanagers;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.TypWypozyczajacy;
import org.example.model.Wypozyczajacy;
import org.junit.jupiter.api.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ZarzadcaWypozyczajacyMongoTest {

    private ZarzadcaWypozyczajacyMongo zarzadca;

    @BeforeAll
    void init() {
        zarzadca = new ZarzadcaWypozyczajacyMongo();
    }

    @BeforeEach
    void clearDatabase() {
        zarzadca.getWypozyczajacyCollection().deleteMany(new Document());
    }

    @AfterAll
    void close() {
        zarzadca.zamknijPolaczenie();
    }

    @Test
    void testDodajWypozyczajacy() {
        TypWypozyczajacy typ = new TypWypozyczajacy(0.5, 14, 5);
        Date testDate = new Date(90, 0, 1); // Rok 1990: 90 = 1990 - 1900, styczeń = 0
        Wypozyczajacy wypozyczajacy = new Wypozyczajacy(typ, "Jan Kowalski", testDate, "ul. Kwiatowa 10");

        zarzadca.dodajWypozyczajacy(wypozyczajacy);

        Wypozyczajacy retrieved = zarzadca.znajdzWypozyczajacy(wypozyczajacy.getId());
        assertNotNull(retrieved, "Wypożyczający nie został dodany do bazy danych.");
        assertEquals("Jan Kowalski", retrieved.getNazwa(), "Nazwa wypożyczającego nie zgadza się.");
        assertEquals("ul. Kwiatowa 10", retrieved.getAdres(), "Adres wypożyczającego nie zgadza się.");
    }

    @Test
    void testUsunWypozyczajacy() {
        TypWypozyczajacy typ = new TypWypozyczajacy(1.0, 30, 10);
        Date testDate = new Date(85, 4, 15); // Rok 1985: 85 = 1985 - 1900, maj = 4
        Wypozyczajacy wypozyczajacy = new Wypozyczajacy(typ, "Anna Nowak", testDate, "ul. Wiosenna 20");

        zarzadca.dodajWypozyczajacy(wypozyczajacy);
        ObjectId wypozyczajacyId = wypozyczajacy.getId();
        zarzadca.usunWypozyczajacy(wypozyczajacyId);

        Wypozyczajacy retrieved = zarzadca.znajdzWypozyczajacy(wypozyczajacyId);
        assertNull(retrieved, "Wypożyczający nie został poprawnie usunięty z bazy danych.");
    }

    @Test
    void testZaktualizujWypozyczajacy() {
        TypWypozyczajacy typ = new TypWypozyczajacy(0.5, 14, 5);
        Date testDate = new Date(92, 6, 25); // Rok 1992: 92 = 1992 - 1900, lipiec = 6
        Wypozyczajacy wypozyczajacy = new Wypozyczajacy(typ, "Ewa Kwiatkowska", testDate, "ul. Jesienna 15");

        zarzadca.dodajWypozyczajacy(wypozyczajacy);

        wypozyczajacy.setAdres("ul. Nowa 42");
        zarzadca.zaktualizujWypozyczajacy(wypozyczajacy.getId(), wypozyczajacy);

        Wypozyczajacy updated = zarzadca.znajdzWypozyczajacy(wypozyczajacy.getId());
        assertNotNull(updated, "Zaktualizowany wypożyczający nie został znaleziony.");
        assertEquals("ul. Nowa 42", updated.getAdres(), "Adres wypożyczającego nie został poprawnie zaktualizowany.");
    }

    @Test
    void testZnajdzWypozyczajacyNotFound() {
        ObjectId fakeId = new ObjectId();

        Wypozyczajacy retrieved = zarzadca.znajdzWypozyczajacy(fakeId);
        assertNull(retrieved, "Nieistniejący wypożyczający został błędnie znaleziony.");
    }
}
