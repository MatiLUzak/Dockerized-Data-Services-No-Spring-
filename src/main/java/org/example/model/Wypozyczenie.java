package org.example.model;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.ObjectId;
import org.example.exceptions.WypozyczenieException;
import java.util.Date;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Wypozyczenie {
    @BsonProperty("wypozyczajacy")
    private Wypozyczajacy wypozyczajacy;
    @BsonProperty("wolumin")
    private Wolumin wolumin;
    @BsonProperty("dataOd")
    private Date dataOd;
    @BsonProperty("dataDo")
    private Date dataDo;
    @BsonId
    private ObjectId id;
    //private UUID uuid;

    // Konstruktor z walidacją
    public Wypozyczenie(Wypozyczajacy wypozyczajacy, Wolumin wolumin) {
        if (wypozyczajacy == null) {
            throw new WypozyczenieException("Błędny wypożyczający");
        }
        if (wolumin == null) {
            throw new WypozyczenieException("Błędny wolumin");
        }
        this.wypozyczajacy = wypozyczajacy;
        this.wolumin = wolumin;
        this.dataOd = new Date();  // Ustawienie daty początkowej na teraz
        //this.uuid = UUID.randomUUID();  // Generowanie UUID
    }
    public Wypozyczenie() {}

    // Gettery
    public Wypozyczajacy getWypozyczajacy() {
        return wypozyczajacy;
    }

    public Wolumin getWolumin() {
        return wolumin;
    }

    public Date getDataOd() {
        return dataOd;
    }

    public Date getDataDo() {
        return dataDo;
    }

    public ObjectId getId() {
        return id;
    }

    public void setDataOd(Date dataOd) {
        this.dataOd = dataOd;
    }

    public void setDataDo(Date dataDo) {
        this.dataDo = dataDo;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    /*public UUID getUuid() {
        return uuid;
    }*/

    // Settery z walidacją
    public void setWypozyczajacy(Wypozyczajacy wypozyczajacy) {
        if (wypozyczajacy == null) {
            throw new WypozyczenieException("Błędny wypożyczający");
        }
        this.wypozyczajacy = wypozyczajacy;
    }

    public void setWolumin(Wolumin wolumin) {
        if (wolumin == null) {
            throw new WypozyczenieException("Błędny wolumin");
        }
        this.wolumin = wolumin;
    }

    // Zakończenie wypożyczenia - ustawienie daty zwrotu
    public void koniecWypozyczenia() {
        this.dataDo = new Date();
    }

    // Obliczanie długości wypożyczenia w dniach
    public double dlugoscWypozyczenia() {
        if (dataDo == null) {
            return 0;
        }
        long diff = dataDo.getTime() - dataOd.getTime();
        return diff / (1000.0 * 60 * 60 * 24);
    }

    // Obliczanie kary na podstawie długości wypożyczenia
    public double obliczKare() {
        return wypozyczajacy.getTypWypozyczajacy().getKara() * dlugoscWypozyczenia();
    }
}
