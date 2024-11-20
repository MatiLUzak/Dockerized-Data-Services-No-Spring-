package org.example.databaserepository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class WoluminMongoRepository extends MongoRepository<Document> {

    public WoluminMongoRepository(MongoCollection<Document> collection) {
        super(collection);
    }

    public Document znajdzPoTytule(String tytul) {
        return collection.find(new Document("tytul", tytul)).first();
    }
}
