package org.example.kafka;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

public class ConsumerMain {
    public static void main(String[] args) {
        MongoCredential credentials = MongoCredential.createCredential(
                "admin", "admin", "adminpassword".toCharArray()
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credentials)
                .applyConnectionString(new ConnectionString(
                        "mongodb://mongodb1:27017,mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single"
                ))
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase db = mongoClient.getDatabase("BookSystem");
        MongoCollection<Document> col = db.getCollection("analizaWypozyczenia");

        AvroWypozyczenieConsumer consumer =
                new AvroWypozyczenieConsumer("grupaWypozyczen", col);
        consumer.start();
    }
}
