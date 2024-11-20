package org.example.databaserepository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class WypozyczenieMongoRepository extends MongoRepository<Document> {

    public WypozyczenieMongoRepository(MongoCollection<Document> collection) {
        super(collection);
    }
}
