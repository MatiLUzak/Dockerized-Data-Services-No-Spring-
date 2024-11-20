package org.example.mappers;

import org.bson.Document;
import org.example.model.Nauczyciel;

public class NauczycielMapper {

    public static Document toDocument(Nauczyciel nauczyciel) {
        if (nauczyciel == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("kara", nauczyciel.getKara());
        doc.append("maxDlWypoz", nauczyciel.getMaxDlWypoz());
        doc.append("maksLKsiazek", nauczyciel.getMaksLKsiazek());
        doc.append("tytul", nauczyciel.getTytul());
        return doc;
    }

    public static Nauczyciel fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        double kara = doc.getDouble("kara");
        int maxDlWypoz = doc.getInteger("maxDlWypoz");
        int maksLKsiazek = doc.getInteger("maksLKsiazek");
        String tytul = doc.getString("tytul");

        return new Nauczyciel(kara, maxDlWypoz, maksLKsiazek, tytul);
    }
}
