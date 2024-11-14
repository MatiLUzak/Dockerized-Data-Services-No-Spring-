package org.example.databaserepository;

import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import org.example.model.Wypozyczajacy;

public class WypozyczajacyMongoRepository extends MongoRepository<Wypozyczajacy> {

    public WypozyczajacyMongoRepository(MongoCollection<Wypozyczajacy> collection) {
        super(collection);
    }

    public Wypozyczajacy znajdzPoNazwisku(String nazwisko) {
        return collection.find(new org.bson.Document("nazwisko", nazwisko)).first();
    }
}
