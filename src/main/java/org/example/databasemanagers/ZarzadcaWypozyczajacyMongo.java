package org.example.databasemanagers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.example.databaserepository.WypozyczajacyMongoRepository;
import org.example.model.Wypozyczajacy;

import static org.bson.codecs.configuration.CodecRegistries.*;

public class ZarzadcaWypozyczajacyMongo {
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final WypozyczajacyMongoRepository wypozyczajacyCollection;

    public ZarzadcaWypozyczajacyMongo() {
        ConnectionString connectionString = new ConnectionString("mongodb://mongodb1:27017," +
                "mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single");
        MongoCredential credentials = MongoCredential.createCredential(
                "admin", "admin", "adminpassword".toCharArray());

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .credential(credentials)
                .codecRegistry(pojoCodecRegistry)
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("CarSystem");

        this.wypozyczajacyCollection = new WypozyczajacyMongoRepository(
                database.getCollection("wypozyczajacy", Wypozyczajacy.class)
        );
    }

    public WypozyczajacyMongoRepository getWypozyczajacyCollection() {
        return wypozyczajacyCollection;
    }

    public void dodajWypozyczajacy(Wypozyczajacy wypozyczajacy) {
        wypozyczajacyCollection.dodaj(wypozyczajacy);
    }

    public Wypozyczajacy znajdzWypozyczajacy(ObjectId id) {
        return wypozyczajacyCollection.znajdzPoId(id);
    }

    public void zaktualizujWypozyczajacy(ObjectId id, Wypozyczajacy updatedWypozyczajacy) {
        wypozyczajacyCollection.zaktualizuj(id, updatedWypozyczajacy);
    }

    public void usunWypozyczajacy(ObjectId id) {
        wypozyczajacyCollection.usun(id);
    }

    public void zamknijPolaczenie() {
        mongoClient.close();
    }
}
