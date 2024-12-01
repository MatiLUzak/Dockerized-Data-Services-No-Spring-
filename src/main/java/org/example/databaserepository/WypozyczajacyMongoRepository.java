package org.example.databaserepository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.mappers.WypozyczajacyMapper;
import org.example.model.Wypozyczajacy;

public class WypozyczajacyMongoRepository extends MongoRepository<Document> {

    public WypozyczajacyMongoRepository(MongoCollection<Document> collection) {
        super(collection);
    }

    public Document znajdzPoNazwie(String nazwa) {
        return collection.find(new Document("nazwa", nazwa)).first();
    }
    public void dodajWypozyczajacy(Wypozyczajacy wypozyczajacy) {
        Document doc = WypozyczajacyMapper.toDocument(wypozyczajacy);
        InsertOneResult result = super.dodaj(doc);
        if (result.getInsertedId() != null) {
            ObjectId generatedId = result.getInsertedId().asObjectId().getValue();
            wypozyczajacy.setId(generatedId);
        }
    }

}
