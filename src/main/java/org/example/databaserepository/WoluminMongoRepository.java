package org.example.databaserepository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.mappers.WoluminMapper;
import org.example.model.Wolumin;

public class WoluminMongoRepository extends MongoRepository<Wolumin> implements WoluminRepository {

    public WoluminMongoRepository(MongoCollection<Document> collection) {
        super(collection);
    }

    @Override
    public void dodajWolumin(Wolumin wolumin) {
        Document doc = WoluminMapper.toDocument(wolumin);
        InsertOneResult result = super.dodaj(doc);
        if (result.getInsertedId() != null) {
            ObjectId generatedId = result.getInsertedId().asObjectId().getValue();
            wolumin.setId(generatedId);
        }
    }


    @Override
    public Wolumin znajdzWolumin(ObjectId id) {
        Document doc = super.znajdzPoId(id);
        if (doc != null) {
            return WoluminMapper.fromDocument(doc);
        }
        return null;
    }

    @Override
    public void zaktualizujWolumin(ObjectId id, Wolumin updatedWolumin) {
        Document doc = WoluminMapper.toDocument(updatedWolumin);
        super.zaktualizuj(id, doc);
    }

    @Override
    public void usunWolumin(ObjectId id) {
        super.usun(id);
    }

    @Override
    public void deleteMany(Document filter) {
        super.deleteMany(filter);
    }
}
