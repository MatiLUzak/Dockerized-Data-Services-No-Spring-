package org.example.databaserepository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.Wolumin;

public class WoluminMongoRepository extends MongoRepository<Wolumin> {
    public WoluminMongoRepository(MongoCollection<Wolumin> collection) {
        super(collection);
    }

    public Wolumin znajdzPoTytule(String tytul) {
        return collection.find(new Document("tytul", tytul)).first();
    }
}
