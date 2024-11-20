package org.example.mappers;

import org.bson.Document;
import org.example.model.TypWypozyczajacy;

public class TypWypozyczajacyMapper {

    public static Document toDocument(TypWypozyczajacy typ) {
        if (typ == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("kara", typ.getKara());
        doc.append("maxDlWypoz", typ.getMaxDlWypoz());
        doc .append("maksLKsiazek", typ.getMaksLKsiazek());
        return doc;
    }

    public static TypWypozyczajacy fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        double kara = doc.getDouble("kara");
        int maxDlWypoz = doc.getInteger("maxDlWypoz");
        int maksLKsiazek = doc.getInteger("maksLKsiazek");

        return new TypWypozyczajacy(kara, maxDlWypoz, maksLKsiazek);
    }
}
