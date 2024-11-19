package org.example.databasemanagers;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.codecs.jsr310.Jsr310CodecProvider;
import org.bson.types.ObjectId;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.example.databaserepository.WypozyczenieMongoRepository;
import org.example.model.Wypozyczenie;

import static org.bson.codecs.configuration.CodecRegistries.*;

public class ZarzadcaWypozyczeniaMongo {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final WypozyczenieMongoRepository wypozyczenieCollection;

    public ZarzadcaWypozyczeniaMongo() {
        // Connection configuration
        ConnectionString connectionString = new ConnectionString("mongodb://mongodb1:27017," +
                "mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single");
        MongoCredential credentials = MongoCredential.createCredential(
                "admin", "admin", "adminpassword".toCharArray());

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(
                        new Jsr310CodecProvider(),
                        PojoCodecProvider.builder().automatic(true).build()
                )
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .credential(credentials)
                .codecRegistry(pojoCodecRegistry)
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("CarSystem");

        this.wypozyczenieCollection = new WypozyczenieMongoRepository(
                database.getCollection("wypozyczenia", Wypozyczenie.class)
        );
        boolean indexExists = false;
        for (Document indexInfo : database.getCollection("wypozyczenia").listIndexes()) {
            if ("wolumin.id_1".equals(indexInfo.getString("name"))) {
                indexExists = true;
                break;
            }
        }

        if (!indexExists) {
            // Create unique partial index
            database.getCollection("wypozyczenia").createIndex(
                    Indexes.ascending("wolumin.id"),
                    new IndexOptions().unique(true).partialFilterExpression(new Document("dataDo", null))
            );
        }
    }

    public WypozyczenieMongoRepository getWypozyczenieCollection() {
        return wypozyczenieCollection;
    }

    public void dodajWypozyczenie(Wypozyczenie wypozyczenie) {
        try {
            wypozyczenieCollection.dodaj(wypozyczenie);
        } catch (MongoWriteException e) {
            if (e.getError().getCategory() == ErrorCategory.DUPLICATE_KEY) {
                throw new RuntimeException("Wolumin jest już wypożyczony.");
            } else {
                throw e;
            }
        }
    }

    public Wypozyczenie znajdzWypozyczenie(ObjectId id) {
        return wypozyczenieCollection.znajdzPoId(id);
    }

    public void zaktualizujWypozyczenie(ObjectId id, Wypozyczenie updatedWypozyczenie) {
        wypozyczenieCollection.zaktualizuj(id, updatedWypozyczenie);
    }

    public void usunWypozyczenie(ObjectId id) {
        wypozyczenieCollection.usun(id);
    }

    public double obliczKare(ObjectId id) {
        Wypozyczenie wypozyczenie = wypozyczenieCollection.znajdzPoId(id);
        if (wypozyczenie == null) {
            throw new RuntimeException("Nie znaleziono wypożyczenia");
        }
        return wypozyczenie.obliczKare();
    }

    public void zamknijPolaczenie() {
        mongoClient.close();
    }
}
