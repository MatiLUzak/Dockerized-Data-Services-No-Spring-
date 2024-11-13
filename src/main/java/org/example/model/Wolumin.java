package org.example.model;

import org.bson.types.ObjectId;
import org.example.exceptions.WoluminException;

public class Wolumin {
    private ObjectId id;
    private String wydawnictwo;
    private String jezyk;
    private String tytul;

    public Wolumin(String wydawnictwo, String jezyk, String tytul) {
        if(wydawnictwo==null||wydawnictwo.isEmpty()){
            throw new WoluminException("Błędne Wydawnictwo");
        }
        if(jezyk==null||jezyk.isEmpty()){
            throw new WoluminException("Błędny język");
        }
        if(tytul==null||tytul.isEmpty()){
            throw new WoluminException("Błędny tytuł");
        }
        this.wydawnictwo = wydawnictwo;
        this.jezyk = jezyk;
        this.tytul = tytul;
    }
    public Wolumin() {}

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getWydawnictwo() {
        return wydawnictwo;
    }

    public String getJezyk() {
        return jezyk;
    }

    public String getTytul() {
        return tytul;
    }

    public void setWydawnictwo(String wydawnictwo) {
        if(wydawnictwo==null||wydawnictwo.isEmpty()){
            throw new WoluminException("Błędne Wydawnictwo");
        }
        this.wydawnictwo = wydawnictwo;
    }

    public void setJezyk(String jezyk) {
        if(jezyk==null||jezyk.isEmpty()){
            throw new WoluminException("Błędny jezyk");
        }
        this.jezyk = jezyk;
    }

    public void setTytul(String tytul) {
        if(tytul==null||tytul.isEmpty()){
            throw new WoluminException("Błdny tytul");
        }
        this.tytul = tytul;
    }
}
