package org.example.mappers;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.Wolumin;

public class WoluminMapper {

    public static Document toDocument(Wolumin wolumin) {
        Document doc = new Document();
        if(wolumin.getId() != null) {
            doc.append("_id", wolumin.getId());
        }
        doc.append("wydawnictwo", wolumin.getWydawnictwo());
        doc.append("jezyk", wolumin.getJezyk());
        doc.append("tytul", wolumin.getTytul());
        return doc;
    }

    public static Wolumin fromDocument(Document doc) {
        Wolumin wolumin = new Wolumin();
        wolumin.setId(doc.getObjectId("_id"));
        wolumin.setWydawnictwo(doc.getString("wydawnictwo"));
        wolumin.setJezyk(doc.getString("jezyk"));
        wolumin.setTytul(doc.getString("tytul"));
        return wolumin;
    }
}
