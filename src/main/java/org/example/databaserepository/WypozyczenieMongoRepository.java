package org.example.databaserepository;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.mappers.WypozyczenieMapper;
import org.example.model.Wypozyczenie;

public class WypozyczenieMongoRepository extends MongoRepository<Document> {

    public WypozyczenieMongoRepository(MongoCollection<Document> collection) {
        super(collection);
    }
    public void dodajWypozyczenie(Wypozyczenie wypozyczenie) {
        Document doc = WypozyczenieMapper.toDocument(wypozyczenie);
        try {
            InsertOneResult result = super.dodaj(doc);
            if (result.getInsertedId() != null) {
                ObjectId generatedId = result.getInsertedId().asObjectId().getValue();
                wypozyczenie.setId(generatedId);
            }
        } catch (MongoWriteException e) {
            if (e.getError().getCategory() == ErrorCategory.DUPLICATE_KEY) {
                throw new RuntimeException("Wolumin jest już wypożyczony.");
            } else {
                throw e;
            }
        }
    }

}
