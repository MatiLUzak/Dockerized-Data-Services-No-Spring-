package org.example.mappers;

import org.bson.Document;
import org.example.model.Uczen;

public class UczenMapper {

    public static Document toDocument(Uczen uczen) {
        if (uczen == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("kara", uczen.getKara());
        doc.append("maxDlWypoz", uczen.getMaxDlWypoz());
        doc.append("maksLKsiazek", uczen.getMaksLKsiazek());
        doc.append("nrSemestru", uczen.getNrSemestru());
        return doc;
    }

    public static Uczen fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        double kara = doc.getDouble("kara");
        int maxDlWypoz = doc.getInteger("maxDlWypoz");
        int maksLKsiazek = doc.getInteger("maksLKsiazek");
        String nrSemestru = doc.getString("nrSemestru");

        return new Uczen(kara, maxDlWypoz, maksLKsiazek, nrSemestru);
    }
}
