package org.example.databasemanagers;

import org.bson.types.ObjectId;
import org.example.model.TypWypozyczajacy;
import org.example.model.Wolumin;
import org.example.model.Wypozyczenie;
import org.example.model.Wypozyczajacy;
import org.junit.jupiter.api.*;

import java.util.Date;

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
    @Test
    void testZaktualizujWypozyczenie() {
        Wolumin wolumin = new Wolumin("Wydawnictwo GHI", "Niemiecki", "Tytuł OPQ");
        zarzadcaWoluminu.dodajWolumin(wolumin);

        Wypozyczajacy wypozyczajacy = new Wypozyczajacy();
        wypozyczajacy.setNazwa("Ewa Kowalska");
        wypozyczajacy.setAdres("Example Address");
        wypozyczajacy.setDataUr(new Date());
        wypozyczajacy.setTypWypozyczajacy(new TypWypozyczajacy(10,20,30));
        zarzadcaWypozyczajacy.dodajWypozyczajacy(wypozyczajacy);

        Wypozyczenie wypozyczenie = new Wypozyczenie(wypozyczajacy, wolumin);
        zarzadca.dodajWypozyczenie(wypozyczenie);
        System.out.println("Id wypożyczenia: " + wypozyczenie.getId());
        ObjectId id = wypozyczenie.getId();

        System.out.println("DataDo przed aktualizacją: " + wypozyczenie.getDataDo());
        wypozyczenie.koniecWypozyczenia();
        System.out.println("DataDo po aktualizacji: " + wypozyczenie.getDataDo());
        zarzadca.zaktualizujWypozyczenie(id, wypozyczenie);


        Wypozyczenie updated = zarzadca.znajdzWypozyczenie(id);
        System.out.println("DataDo odczytane z bazy: " + updated.getDataDo());

        // Assertions
        assertNotNull(updated, "Zaktualizowane wypożyczenie nie zostało znalezione.");
        assertNotNull(updated.getDataDo(), "Data zakończenia wypożyczenia nie została ustawiona.");
    }
    @Test
    void testDodajWypozyczenie_Sukces() {
        // Arrange
        Wolumin wolumin = new Wolumin("Wydawnictwo XYZ", "Polski", "Tytuł ABC");
        zarzadcaWoluminu.dodajWolumin(wolumin);

        Wypozyczajacy wypozyczajacy = new Wypozyczajacy();
        wypozyczajacy.setNazwa("Jan Nowak");
        wypozyczajacy.setAdres("Adres 1");
        wypozyczajacy.setDataUr(new Date());
        wypozyczajacy.setTypWypozyczajacy(new TypWypozyczajacy(1, 2, 3));
        zarzadcaWypozyczajacy.dodajWypozyczajacy(wypozyczajacy);

        Wypozyczenie wypozyczenie = new Wypozyczenie(wypozyczajacy, wolumin);

        // Act & Assert
        assertDoesNotThrow(() -> zarzadca.dodajWypozyczenie(wypozyczenie));
    }

    @Test
    void testDodajWypozyczenie_WoluminJuzWypozyczony() {
        // Arrange
        Wolumin wolumin = new Wolumin("Wydawnictwo XYZ", "Polski", "Tytuł ABC");
        zarzadcaWoluminu.dodajWolumin(wolumin);

        Wypozyczajacy wypozyczajacy1 = new Wypozyczajacy();
        wypozyczajacy1.setNazwa("Jan Nowak");
        wypozyczajacy1.setAdres("Adres 1");
        wypozyczajacy1.setDataUr(new Date());
        wypozyczajacy1.setTypWypozyczajacy(new TypWypozyczajacy(1, 2, 3));
        zarzadcaWypozyczajacy.dodajWypozyczajacy(wypozyczajacy1);

        Wypozyczajacy wypozyczajacy2 = new Wypozyczajacy();
        wypozyczajacy2.setNazwa("Anna Kowalska");
        wypozyczajacy2.setAdres("Adres 2");
        wypozyczajacy2.setDataUr(new Date());
        wypozyczajacy2.setTypWypozyczajacy(new TypWypozyczajacy(4, 5, 6));
        zarzadcaWypozyczajacy.dodajWypozyczajacy(wypozyczajacy2);

        // Pierwsze wypożyczenie
        Wypozyczenie wypozyczenie1 = new Wypozyczenie(wypozyczajacy1, wolumin);
        zarzadca.dodajWypozyczenie(wypozyczenie1);

        // Próba drugiego wypożyczenia tego samego woluminu
        Wypozyczenie wypozyczenie2 = new Wypozyczenie(wypozyczajacy2, wolumin);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            zarzadca.dodajWypozyczenie(wypozyczenie2);
        });

        String expectedMessage = "Wolumin jest już wypożyczony.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testWypozyczeniePoZwrocie() {
        // Arrange
        Wolumin wolumin = new Wolumin("Wydawnictwo XYZ", "Polski", "Tytuł ABC");
        zarzadcaWoluminu.dodajWolumin(wolumin);

        Wypozyczajacy wypozyczajacy1 = new Wypozyczajacy();
        wypozyczajacy1.setNazwa("Jan Nowak");
        wypozyczajacy1.setAdres("Adres 1");
        wypozyczajacy1.setDataUr(new Date());
        wypozyczajacy1.setTypWypozyczajacy(new TypWypozyczajacy(1, 2, 3));
        zarzadcaWypozyczajacy.dodajWypozyczajacy(wypozyczajacy1);

        Wypozyczajacy wypozyczajacy2 = new Wypozyczajacy();
        wypozyczajacy2.setNazwa("Anna Kowalska");
        wypozyczajacy2.setAdres("Adres 2");
        wypozyczajacy2.setDataUr(new Date());
        wypozyczajacy2.setTypWypozyczajacy(new TypWypozyczajacy(4, 5, 6));
        zarzadcaWypozyczajacy.dodajWypozyczajacy(wypozyczajacy2);

        // Pierwsze wypożyczenie
        Wypozyczenie wypozyczenie1 = new Wypozyczenie(wypozyczajacy1, wolumin);
        zarzadca.dodajWypozyczenie(wypozyczenie1);

        // Zwrot woluminu
        wypozyczenie1.koniecWypozyczenia();
        zarzadca.zaktualizujWypozyczenie(wypozyczenie1.getId(), wypozyczenie1);

        // Drugie wypożyczenie po zwrocie
        Wypozyczenie wypozyczenie2 = new Wypozyczenie(wypozyczajacy2, wolumin);

        // Act & Assert
        assertDoesNotThrow(() -> zarzadca.dodajWypozyczenie(wypozyczenie2));
    }


}
