package org.example.databasemanagers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.databaserepository.WoluminMongoRepository;
import org.example.mappers.WoluminMapper;
import org.example.model.Wolumin;

public class ZarzadcaWoluminu {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final WoluminMongoRepository repozytorium;


    public ZarzadcaWoluminu() {
        // Konfiguracja połączenia
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
        this.repozytorium = new WoluminMongoRepository(database.getCollection("woluminy", Document.class));
    }

    public void dodajWolumin(Wolumin wolumin) {
        Document doc = WoluminMapper.toDocument(wolumin);
        repozytorium.dodaj(doc);
    }

    public Wolumin znajdzWolumin(ObjectId id) {
        Document doc = database.getCollection("woluminy").find(new Document("_id", id)).first();
        if (doc != null) {
            return WoluminMapper.fromDocument(doc);
        } else {
            return null;
        }
    }

    public void zaktualizujWolumin(ObjectId id, Wolumin updatedWolumin) {
        Document doc = WoluminMapper.toDocument(updatedWolumin);
        repozytorium.zaktualizuj(id, doc);
    }

    public void usunWolumin(ObjectId id) {
        repozytorium.usun(id);
    }

    public void zamknijPolaczenie() {
        mongoClient.close();
    }

    public com.mongodb.client.MongoCollection<Document> getWoluminCollection() {
        return database.getCollection("woluminy");
    }

}
