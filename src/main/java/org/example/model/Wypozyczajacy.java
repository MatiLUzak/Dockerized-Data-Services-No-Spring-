package org.example.model;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.ObjectId;
import org.example.exceptions.WypozyczajacyException;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Wypozyczajacy {
    @BsonProperty("typWypozyczajacy")
    private TypWypozyczajacy typWypozyczajacy;
    @BsonProperty("nazwa")
    private String nazwa;
    @BsonProperty("dataUr")
    private Date dataUr;
    @BsonProperty("adres")
    private String adres;
    @BsonId
    private ObjectId id;
    //private UUID uuid;

    public Wypozyczajacy(TypWypozyczajacy typWypozyczajacy, String nazwa, Date dataUr, String adres) {
        if (typWypozyczajacy == null) {
            throw new WypozyczajacyException("Błędny typWypozyczajacy");
        }
        if (nazwa == null || nazwa.isEmpty()) {
            throw new WypozyczajacyException("Błędna nazwa");
        }
        if (adres == null || adres.isEmpty()) {
            throw new WypozyczajacyException("Błędny adres");
        }

        this.typWypozyczajacy = typWypozyczajacy;
        this.nazwa = nazwa;
        this.dataUr = dataUr;
        this.adres = adres;
        //this.uuid = UUID.randomUUID();
    }
    public Wypozyczajacy() {}

    /*public UUID getUuid() {
        return uuid;
    }
     */

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public TypWypozyczajacy getTypWypozyczajacy() {
        return typWypozyczajacy;
    }

    public String getNazwa() {
        return nazwa;
    }

    public Date getDataUr() {
        return dataUr;
    }

    public String getAdres() {
        return adres;
    }

    public void setTypWypozyczajacy(TypWypozyczajacy typWypozyczajacy) {
        if (typWypozyczajacy == null) {
            throw new WypozyczajacyException("Błędny typWypozyczajacy");
        }
        this.typWypozyczajacy = typWypozyczajacy;
    }

    public void setNazwa(String nazwa) {
        if (nazwa == null || nazwa.isEmpty()) {
            throw new WypozyczajacyException("Błędna nazwa");
        }
        this.nazwa = nazwa;
    }

    public void setDataUr(Date dataUr) {
        this.dataUr = dataUr;
    }

    public void setAdres(String adres) {
        if (adres == null || adres.isEmpty()) {
            throw new WypozyczajacyException("Błędny adres");
        }
        this.adres = adres;
    }

    public String pobierzInformacjeWypozyczajacego() {
        StringBuilder info = new StringBuilder();
        info.append("Nazwa: ").append(getNazwa()).append("\n");
        info.append("Data urodzenia: ").append(getDataUr()).append("\n");
        info.append("Adres: ").append(getAdres()).append("\n");
        if (typWypozyczajacy != null) {
            info.append("Typ wypożyczającego: ").append(typWypozyczajacy.pobierzInfo()).append("\n");
        }
        return info.toString();
    }
}
