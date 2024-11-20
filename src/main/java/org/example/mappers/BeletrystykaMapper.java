package org.example.mappers;

import org.bson.Document;
import org.example.model.Beletrystyka;

import java.util.List;

public class BeletrystykaMapper {

    public static Document toDocument(Beletrystyka beletrystyka) {
        if (beletrystyka == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("wydawnictwo", beletrystyka.getWydawnictwo());
        doc.append("jezyk", beletrystyka.getJezyk());
        doc.append("tytul", beletrystyka.getTytul());
        doc.append("autorzy", beletrystyka.getAutor());
        doc.append("przedzialWiekowy", beletrystyka.getPrzedział_wiekowy());
        doc.append("rodzaj", beletrystyka.getRodzaj());
        return doc;
    }

    public static Beletrystyka fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        String wydawnictwo = doc.getString("wydawnictwo");
        String jezyk = doc.getString("jezyk");
        String tytul = doc.getString("tytul");
        List<String> autorzy = (List<String>) doc.get("autorzy");
        String przedzialWiekowy = doc.getString("przedzialWiekowy");
        String rodzaj = doc.getString("rodzaj");

        return new Beletrystyka(wydawnictwo, jezyk, tytul, autorzy, przedzialWiekowy, rodzaj);
    }
}
