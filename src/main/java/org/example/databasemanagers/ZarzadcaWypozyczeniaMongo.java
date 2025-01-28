package org.example.databasemanagers;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import kafka.WypozyczenieEvent;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.databaserepository.WypozyczenieMongoRepository;
import org.example.kafka.AvroWypozyczenieProducer;
import org.example.mappers.WypozyczenieMapper;
import org.example.model.Wypozyczenie;
import org.example.model.Wypozyczajacy;
import org.example.model.Wolumin;

public class ZarzadcaWypozyczeniaMongo {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final ZarzadcaWypozyczajacyMongo wypozyczajacyZarzadca;
    private final ZarzadcaWoluminu woluminZarzadca;
    private final WypozyczenieMongoRepository repozytorium;
    private final AvroWypozyczenieProducer producer;

    public ZarzadcaWypozyczeniaMongo(ZarzadcaWypozyczajacyMongo wypozyczajacyZarzadca, ZarzadcaWoluminu woluminZarzadca) {
        MongoCredential credentials = MongoCredential.createCredential(
                "admin", "admin", "adminpassword".toCharArray()
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credentials)
                .applyConnectionString(new ConnectionString("mongodb://mongodb1:27017," +
                        "mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single"))
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("BookSystem");
        this.wypozyczajacyZarzadca = wypozyczajacyZarzadca;
        this.woluminZarzadca = woluminZarzadca;
        this.repozytorium = new WypozyczenieMongoRepository(database.getCollection("wypozyczenia", Document.class));

        boolean indexExists = false;
        for (Document indexInfo : database.getCollection("wypozyczenia").listIndexes()) {
            if ("woluminId_1".equals(indexInfo.getString("name"))) {
                indexExists = true;
                break;
            }
        }

        if (!indexExists) {
            database.getCollection("wypozyczenia").createIndex(
                    Indexes.ascending("woluminId"),
                    new IndexOptions().unique(true).partialFilterExpression(new Document("dataDo", null))
            );
        }
        this.producer = new AvroWypozyczenieProducer();
    }

    public void dodajWypozyczenie(Wypozyczenie wypozyczenie) {
        Document doc = WypozyczenieMapper.toDocument(wypozyczenie);
        try {
            repozytorium.dodajWypozyczenie(wypozyczenie);
        } catch (MongoWriteException e) {
            if (e.getError().getCategory() == ErrorCategory.DUPLICATE_KEY) {
                throw new RuntimeException("Wolumin jest już wypożyczony.");
            } else {
                throw e;
            }
        }
        WypozyczenieEvent event = WypozyczenieEvent.newBuilder()
                .setIdWypozyczenia(wypozyczenie.getId() != null ? wypozyczenie.getId().toHexString() : null)
                .setNazwaWypozyczalni("MojaSuperWypozyczalniaX")
                .setNazwaWypozyczajacego(
                        wypozyczenie.getWypozyczajacy() != null ? wypozyczenie.getWypozyczajacy().getNazwa() : null
                )
                .setTytulWoluminu(
                        wypozyczenie.getWolumin() != null ? wypozyczenie.getWolumin().getTytul() : null
                )
                .setDataOd(wypozyczenie.getDataOd()!=null ? wypozyczenie.getDataOd().getTime() : null)
                .setDataDo(wypozyczenie.getDataDo()!=null ? wypozyczenie.getDataDo().getTime() : null)
                .build();

        producer.sendWypozyczenie(event);
    }

    public Wypozyczenie znajdzWypozyczenie(ObjectId id) {
        Document doc = database.getCollection("wypozyczenia").find(new Document("_id", id)).first();
        if (doc != null) {
            ObjectId wypozyczajacyId = doc.getObjectId("wypozyczajacyId");
            ObjectId woluminId = doc.getObjectId("woluminId");

            Wypozyczajacy wypozyczajacy = wypozyczajacyZarzadca.znajdzWypozyczajacy(wypozyczajacyId);
            Wolumin wolumin = woluminZarzadca.znajdzWolumin(woluminId);

            return WypozyczenieMapper.fromDocument(doc, wypozyczajacy, wolumin);
        } else {
            return null;
        }
    }

    public void zaktualizujWypozyczenie(ObjectId id, Wypozyczenie updatedWypozyczenie) {
        Document doc = WypozyczenieMapper.toDocument(updatedWypozyczenie);
        repozytorium.zaktualizuj(id, doc);
    }

    public void usunWypozyczenie(ObjectId id) {
        repozytorium.usun(id);
    }

    public void zamknijPolaczenie() {
        producer.close();
        mongoClient.close();
    }

    public com.mongodb.client.MongoCollection<Document> getWypozyczenieCollection() {
        return database.getCollection("wypozyczenia");
    }

}
