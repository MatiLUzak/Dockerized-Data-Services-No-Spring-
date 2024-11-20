package org.example.mappers;

import org.bson.Document;
import org.example.model.Pozostali;

public class PozostaliMapper {

    public static Document toDocument(Pozostali pozostali) {
        if (pozostali == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("kara", pozostali.getKara());
        doc.append("maxDlWypoz", pozostali.getMaxDlWypoz());
        doc.append("maksLKsiazek", pozostali.getMaksLKsiazek());
        doc.append("zawod", pozostali.getZawod());
        return doc;
    }

    public static Pozostali fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }
        double kara = doc.getDouble("kara");
        int maxDlWypoz = doc.getInteger("maxDlWypoz");
        int maksLKsiazek = doc.getInteger("maksLKsiazek");
        String zawod = doc.getString("zawod");

        return new Pozostali(kara, maxDlWypoz, maksLKsiazek, zawod);
    }
}
