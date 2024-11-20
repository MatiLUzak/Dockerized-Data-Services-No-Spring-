package org.example.mappers;

import org.bson.Document;
import org.example.model.Ksiazka;

import java.util.List;

public class KsiazkaMapper {

    public static Document toDocument(Ksiazka ksiazka) {
        if (ksiazka == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("wydawnictwo", ksiazka.getWydawnictwo());
        doc.append("jezyk", ksiazka.getJezyk());
        doc.append("tytul", ksiazka.getTytul());
        doc.append("listaAutorow", ksiazka.getAutor());
        return doc;
    }

    public static Ksiazka fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        String wydawnictwo = doc.getString("wydawnictwo");
        String jezyk = doc.getString("jezyk");
        String tytul = doc.getString("tytul");
        List<String> autor = (List<String>) doc.get("listaAutorow");

        return new Ksiazka(wydawnictwo, jezyk, tytul, autor);
    }
}
