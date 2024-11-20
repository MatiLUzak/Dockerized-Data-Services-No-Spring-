package org.example.databaserepository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

public abstract class MongoRepository<T> {
    protected final MongoCollection<Document> collection;

    public MongoRepository(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public void dodaj(Document entity) {
        collection.insertOne(entity);
    }

    public Document znajdzPoId(ObjectId id) {
        return collection.find(new Document("_id", id)).first();
    }

    public void zaktualizuj(ObjectId id, Document updatedEntity) {
        collection.replaceOne(new Document("_id", id), updatedEntity);
    }

    public void usun(ObjectId id) {
        collection.deleteOne(new Document("_id", id));
    }

    public void deleteMany(Document filter) {
        collection.deleteMany(filter);
    }
}
