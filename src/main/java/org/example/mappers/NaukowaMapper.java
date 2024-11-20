package org.example.mappers;

import org.bson.Document;
import org.example.model.Naukowa;

import java.util.List;

public class NaukowaMapper {

    public static Document toDocument(Naukowa naukowa) {
        if (naukowa == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("wydawnictwo", naukowa.getWydawnictwo());
        doc.append("jezyk", naukowa.getJezyk());
        doc.append("tytul", naukowa.getTytul());
        doc.append("autorzy", naukowa.getAutor());
        doc.append("recenzja", naukowa.getRecenzja());
        doc.append("dział", naukowa.getDział());
        return doc;
    }

    public static Naukowa fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        String wydawnictwo = doc.getString("wydawnictwo");
        String jezyk = doc.getString("jezyk");
        String tytul = doc.getString("tytul");
        List<String> autorzy = (List<String>) doc.get("autorzy");
        String recenzja = doc.getString("recenzja");
        String dział = doc.getString("dział");

        return new Naukowa(wydawnictwo, jezyk, tytul, autorzy, recenzja, dział);
    }
}
