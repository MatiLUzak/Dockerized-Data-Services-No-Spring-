package org.example.databaserepository;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.Wolumin;

public interface WoluminRepository {
    void dodajWolumin(Wolumin wolumin);
    Wolumin znajdzWolumin(ObjectId id);
    void zaktualizujWolumin(ObjectId id, Wolumin updatedWolumin);
    void usunWolumin(ObjectId id);
    void deleteMany(Document filter);
}
