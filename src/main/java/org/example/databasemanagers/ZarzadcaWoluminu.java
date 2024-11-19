package org.example.databasemanagers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.jsr310.Jsr310CodecProvider;
import org.bson.types.ObjectId;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.example.databaserepository.WoluminMongoRepository;
import org.example.model.Wolumin;

import static org.bson.codecs.configuration.CodecRegistries.*;

public class ZarzadcaWoluminu {
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final WoluminMongoRepository woluminCollection;

    public ZarzadcaWoluminu() {
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

        this.woluminCollection = new WoluminMongoRepository(
                database.getCollection("woluminy", Wolumin.class)
        );
    }

    public WoluminMongoRepository getWoluminCollection() {
        return woluminCollection;
    }

    public void dodajWolumin(Wolumin wolumin) {
        woluminCollection.dodaj(wolumin);
    }

    public Wolumin znajdzWolumin(ObjectId id) {
        return woluminCollection.znajdzPoId(id);
    }

    public void zaktualizujWolumin(ObjectId id, Wolumin updatedWolumin) {
        woluminCollection.zaktualizuj(id, updatedWolumin);
    }

    public void usunWolumin(ObjectId id) {
        woluminCollection.usun(id);
    }

    public Wolumin znajdzPoTytule(String tytul) {
        return woluminCollection.znajdzPoTytule(tytul);
    }

    public void zamknijPolaczenie() {
        mongoClient.close();
    }
}
