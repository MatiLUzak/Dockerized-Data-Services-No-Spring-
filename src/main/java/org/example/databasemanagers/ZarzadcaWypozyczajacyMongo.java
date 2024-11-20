package org.example.databasemanagers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.mappers.WypozyczajacyMapper;
import org.example.model.Wypozyczajacy;

public class ZarzadcaWypozyczajacyMongo {

    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public ZarzadcaWypozyczajacyMongo() {
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
    }

    public void dodajWypozyczajacy(Wypozyczajacy wypozyczajacy) {
        Document doc = WypozyczajacyMapper.toDocument(wypozyczajacy);
        database.getCollection("wypozyczajacy").insertOne(doc);
    }

    public Wypozyczajacy znajdzWypozyczajacy(ObjectId id) {
        Document doc = database.getCollection("wypozyczajacy").find(new Document("_id", id)).first();
        if (doc != null) {
            return WypozyczajacyMapper.fromDocument(doc);
        } else {
            return null;
        }
    }

    public void zaktualizujWypozyczajacy(ObjectId id, Wypozyczajacy updatedWypozyczajacy) {
        Document doc = WypozyczajacyMapper.toDocument(updatedWypozyczajacy);
        database.getCollection("wypozyczajacy").replaceOne(new Document("_id", id), doc);
    }

    public void usunWypozyczajacy(ObjectId id) {
        database.getCollection("wypozyczajacy").deleteOne(new Document("_id", id));
    }

    public void zamknijPolaczenie() {
        mongoClient.close();
    }
    public com.mongodb.client.MongoCollection<Document> getWypozyczajacyCollection() {
        return database.getCollection("wypozyczajacy");
    }

}
