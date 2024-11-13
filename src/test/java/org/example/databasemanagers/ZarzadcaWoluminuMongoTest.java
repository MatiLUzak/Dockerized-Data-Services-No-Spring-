package org.example.databasemanagers;

import org.bson.types.ObjectId;
import org.example.model.Wolumin;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ZarzadcaWoluminuTest {

    private ZarzadcaWoluminu zarzadca;

    @BeforeAll
    void init() {
        zarzadca = new ZarzadcaWoluminu();
    }

    @BeforeEach
    void clearDatabase() {
        zarzadca.getWoluminCollection().deleteMany(new org.bson.Document());
    }

    @AfterAll
    void close() {
        zarzadca.zamknijPolaczenie();
    }

    @Test
    void testDodajWolumin() {
        Wolumin wolumin = new Wolumin("Wydawnictwo ABC", "Polski", "Tytuł XYZ");

        zarzadca.dodajWolumin(wolumin);

        Wolumin retrieved = zarzadca.znajdzWolumin(wolumin.getId());
        assertNotNull(retrieved, "Wolumin nie został dodany do bazy danych.");
        assertEquals("Tytuł XYZ", retrieved.getTytul(), "Tytuł woluminu nie zgadza się.");
        assertEquals("Polski", retrieved.getJezyk(), "Język woluminu nie zgadza się.");
    }

    @Test
    void testUsunWolumin() {
        Wolumin wolumin = new Wolumin("Wydawnictwo DEF", "Angielski", "Tytuł LMN");
        zarzadca.dodajWolumin(wolumin);
        ObjectId woluminId = wolumin.getId();

        zarzadca.usunWolumin(woluminId);

        Wolumin retrieved = zarzadca.znajdzWolumin(woluminId);
        assertNull(retrieved, "Wolumin nie został poprawnie usunięty z bazy danych.");
    }

    @Test
    void testZaktualizujWolumin() {
        Wolumin wolumin = new Wolumin("Wydawnictwo GHI", "Niemiecki", "Tytuł OPQ");
        zarzadca.dodajWolumin(wolumin);
        ObjectId woluminId = wolumin.getId();

        wolumin.setTytul("Nowy Tytuł");
        zarzadca.zaktualizujWolumin(woluminId, wolumin);

        Wolumin updated = zarzadca.znajdzWolumin(woluminId);
        assertNotNull(updated, "Zaktualizowany wolumin nie został znaleziony w bazie danych.");
        assertEquals("Nowy Tytuł", updated.getTytul(), "Tytuł woluminu nie został poprawnie zaktualizowany.");
        assertEquals("Niemiecki", updated.getJezyk(), "Język woluminu nie powinien się zmienić.");
    }

    @Test
    void testZnajdzWoluminNotFound() {
        ObjectId fakeId = new ObjectId();

        Wolumin retrieved = zarzadca.znajdzWolumin(fakeId);
        assertNull(retrieved, "Nieistniejący wolumin został błędnie znaleziony.");
    }
}
