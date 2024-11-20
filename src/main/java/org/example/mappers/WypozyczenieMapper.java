package org.example.mappers;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.Wypozyczenie;
import org.example.model.Wypozyczajacy;
import org.example.model.Wolumin;

public class WypozyczenieMapper {

    public static Document toDocument(Wypozyczenie wypozyczenie) {
        Document doc = new Document();
        doc.append("_id", wypozyczenie.getId());
        doc.append("wypozyczajacyId", wypozyczenie.getWypozyczajacy().getId());
        doc.append("woluminId", wypozyczenie.getWolumin().getId());
        doc.append("dataOd", wypozyczenie.getDataOd());
        doc.append("dataDo", wypozyczenie.getDataDo());
        return doc;
    }

    public static Wypozyczenie fromDocument(Document doc, Wypozyczajacy wypozyczajacy, Wolumin wolumin) {
        Wypozyczenie wypozyczenie = new Wypozyczenie();
        wypozyczenie.setId(doc.getObjectId("_id"));
        wypozyczenie.setWypozyczajacy(wypozyczajacy);
        wypozyczenie.setWolumin(wolumin);
        wypozyczenie.setDataOd(doc.getDate("dataOd"));
        wypozyczenie.setDataDo(doc.getDate("dataDo"));
        return wypozyczenie;
    }
}
