package org.example.mappers;

import org.bson.Document;
import org.example.model.Czasopismo;

public class CzasopismoMapper {

    public static Document toDocument(Czasopismo czasopismo) {
        if (czasopismo == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("wydawnictwo", czasopismo.getWydawnictwo());
        doc.append("jezyk", czasopismo.getJezyk());
        doc.append("tytul", czasopismo.getTytul());
        doc.append("nrWydania", czasopismo.getNrWydania());
        return doc;
    }

    public static Czasopismo fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        String wydawnictwo = doc.getString("wydawnictwo");
        String jezyk = doc.getString("jezyk");
        String tytul = doc.getString("tytul");
        String nrWydania = doc.getString("nrWydania");

        return new Czasopismo(wydawnictwo, jezyk, tytul, nrWydania);
    }
}
