package org.example.databasemanagers;

import org.bson.types.ObjectId;
import org.example.model.Wolumin;
import org.example.model.Wypozyczenie;
import org.example.model.Wypozyczajacy;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ZarzadcaWypozyczeniaMongoTest {

    private ZarzadcaWypozyczeniaMongo zarzadca;
    private ZarzadcaWoluminu zarzadcaWoluminu;
    private ZarzadcaWypozyczajacyMongo zarzadcaWypozyczajacy;

    @BeforeAll
    void init() {
        zarzadca = new ZarzadcaWypozyczeniaMongo();
        zarzadcaWoluminu = new ZarzadcaWoluminu();
        zarzadcaWypozyczajacy = new ZarzadcaWypozyczajacyMongo();
    }

    @BeforeEach
    void clearDatabase() {
        zarzadca.getWypozyczenieCollection().deleteMany(new org.bson.Document());
        zarzadcaWoluminu.getWoluminCollection().deleteMany(new org.bson.Document());
        zarzadcaWypozyczajacy.getWypozyczajacyCollection().deleteMany(new org.bson.Document());
    }

    @AfterAll
    void close() {
        zarzadca.zamknijPolaczenie();
        zarzadcaWoluminu.zamknijPolaczenie();
        zarzadcaWypozyczajacy.zamknijPolaczenie();
    }

    @Test
    void testDodajWypozyczenie() {
        Wolumin wolumin = new Wolumin("Wydawnictwo ABC", "Polski", "Tytuł XYZ");
        zarzadcaWoluminu.dodajWolumin(wolumin);

        Wypozyczajacy wypozyczajacy = new Wypozyczajacy();
        wypozyczajacy.setNazwa("Jan Kowalski");
        zarzadcaWypozyczajacy.dodajWypozyczajacy(wypozyczajacy);

        Wypozyczenie wypozyczenie = new Wypozyczenie(wypozyczajacy, wolumin);
        zarzadca.dodajWypozyczenie(wypozyczenie);

        Wypozyczenie retrieved = zarzadca.znajdzWypozyczenie(wypozyczenie.getId());
        assertNotNull(retrieved, "Wypożyczenie nie zostało dodane do bazy danych.");
        assertEquals(wypozyczajacy.getNazwa(), retrieved.getWypozyczajacy().getNazwa(), "Niepoprawny wypożyczający.");
        assertEquals(wolumin.getTytul(), retrieved.getWolumin().getTytul(), "Niepoprawny wolumin.");
    }

    @Test
    void testUsunWypozyczenie() {
        Wolumin wolumin = new Wolumin("Wydawnictwo DEF", "Angielski", "Tytuł LMN");
        zarzadcaWoluminu.dodajWolumin(wolumin);

        Wypozyczajacy wypozyczajacy = new Wypozyczajacy();
        wypozyczajacy.setNazwa("Anna Nowak");
        zarzadcaWypozyczajacy.dodajWypozyczajacy(wypozyczajacy);

        Wypozyczenie wypozyczenie = new Wypozyczenie(wypozyczajacy, wolumin);
        zarzadca.dodajWypozyczenie(wypozyczenie);
        ObjectId id = wypozyczenie.getId();

        zarzadca.usunWypozyczenie(id);

        Wypozyczenie retrieved = zarzadca.znajdzWypozyczenie(id);
        assertNull(retrieved, "Wypożyczenie nie zostało poprawnie usunięte z bazy danych.");
    }

}
