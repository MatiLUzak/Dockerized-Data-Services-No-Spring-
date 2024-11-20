package org.example.databaserepository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class WypozyczajacyMongoRepository extends MongoRepository<Document> {

    public WypozyczajacyMongoRepository(MongoCollection<Document> collection) {
        super(collection);
    }

    public Document znajdzPoNazwie(String nazwa) {
        return collection.find(new Document("nazwa", nazwa)).first();
    }
}
