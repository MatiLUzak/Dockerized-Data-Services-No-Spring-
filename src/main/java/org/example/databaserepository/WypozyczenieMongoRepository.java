package org.example.databaserepository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.Wypozyczenie;

public class WypozyczenieMongoRepository extends MongoRepository<Wypozyczenie> {

    public WypozyczenieMongoRepository(MongoCollection<Wypozyczenie> collection) {
        super(collection);
    }
}
