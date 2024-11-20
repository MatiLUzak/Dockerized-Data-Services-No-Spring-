package org.example.mappers;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.TypWypozyczajacy;
import org.example.model.Wypozyczajacy;

import java.util.Date;

public class WypozyczajacyMapper {

    public static Document toDocument(Wypozyczajacy wypozyczajacy) {
        Document doc = new Document();
        doc.append("_id", wypozyczajacy.getId());
        doc.append("nazwa", wypozyczajacy.getNazwa());
        doc.append("adres", wypozyczajacy.getAdres());
        doc.append("dataUr", wypozyczajacy.getDataUr());
        doc.append("typWypozyczajacy", TypWypozyczajacyMapper.toDocument(wypozyczajacy.getTypWypozyczajacy()));
        return doc;
    }

    public static Wypozyczajacy fromDocument(Document doc) {
        Wypozyczajacy wypozyczajacy = new Wypozyczajacy();
        wypozyczajacy.setId(doc.getObjectId("_id"));
        wypozyczajacy.setNazwa(doc.getString("nazwa"));
        wypozyczajacy.setAdres(doc.getString("adres"));
        wypozyczajacy.setDataUr(doc.getDate("dataUr"));
        TypWypozyczajacy typ = TypWypozyczajacyMapper.fromDocument((Document) doc.get("typWypozyczajacy"));
        wypozyczajacy.setTypWypozyczajacy(typ);
        return wypozyczajacy;
    }
}
