package org.example.databasemanagers;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.TypWypozyczajacy;
import org.example.model.Wypozyczajacy;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

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
        Wypozyczajacy wypozyczajacy = new Wypozyczajacy(typ, "Jan Kowalski", LocalDate.of(1990, 1, 1), "ul. Kwiatowa 10");

        zarzadca.dodajWypozyczajacy(wypozyczajacy);

        Wypozyczajacy retrieved = zarzadca.znajdzWypozyczajacy(wypozyczajacy.getId());
        assertNotNull(retrieved, "Wypożyczający nie został dodany do bazy danych.");
        assertEquals("Jan Kowalski", retrieved.getNazwa(), "Nazwa wypożyczającego nie zgadza się.");
        assertEquals("ul. Kwiatowa 10", retrieved.getAdres(), "Adres wypożyczającego nie zgadza się.");
    }

    @Test
    void testUsunWypozyczajacy() {
        TypWypozyczajacy typ = new TypWypozyczajacy(1.0, 30, 10);
        Wypozyczajacy wypozyczajacy = new Wypozyczajacy(typ, "Anna Nowak", LocalDate.of(1985, 5, 15), "ul. Wiosenna 20");

        zarzadca.dodajWypozyczajacy(wypozyczajacy);
        ObjectId wypozyczajacyId = wypozyczajacy.getId();
        zarzadca.usunWypozyczajacy(wypozyczajacyId);

        Wypozyczajacy retrieved = zarzadca.znajdzWypozyczajacy(wypozyczajacyId);
        assertNull(retrieved, "Wypożyczający nie został poprawnie usunięty z bazy danych.");
    }

    @Test
    void testZaktualizujWypozyczajacy() {
        TypWypozyczajacy typ = new TypWypozyczajacy(0.5, 14, 5);
        Wypozyczajacy wypozyczajacy = new Wypozyczajacy(typ, "Ewa Kwiatkowska", LocalDate.of(1992, 7, 25), "ul. Jesienna 15");

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
