package org.example.model;

import org.bson.types.ObjectId;
import org.example.exceptions.WypozyczenieException;
import java.util.Date;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Wypozyczenie {
    private Wypozyczajacy wypozyczajacy;
    private Wolumin wolumin;
    private LocalDateTime dataOd;
    private LocalDateTime dataDo;
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
        this.dataOd = LocalDateTime.now();  // Ustawienie daty początkowej na teraz
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

    public LocalDateTime getDataOd() {
        return dataOd;
    }

    public LocalDateTime getDataDo() {
        return dataDo;
    }

    public ObjectId getId() {
        return id;
    }

    public void setDataOd(LocalDateTime dataOd) {
        this.dataOd = dataOd;
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
        this.dataDo = LocalDateTime.now();
    }

    // Obliczanie długości wypożyczenia w dniach
    public double dlugoscWypozyczenia() {
        if (dataDo == null) {
            return 0;
        }
        Duration duration = Duration.between(dataOd, dataDo);
        return (double) duration.toDays();
    }

    // Obliczanie kary na podstawie długości wypożyczenia
    public double obliczKare() {
        return wypozyczajacy.getTypWypozyczajacy().getKara() * dlugoscWypozyczenia();
    }
}
