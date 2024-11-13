package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class TestMongoConnection {

    public static void main(String[] args) {
        // Connection details
        ConnectionString connectionString = new ConnectionString("mongodb://mongodb1:27017," +
                "mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single");
        MongoCredential credentials = MongoCredential.createCredential(
                "admin", "admin", "adminpassword".toCharArray());

        // Codec registry for POJO support
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder()
                        .automatic(true)
                        .build())
        );

        // MongoClient settings
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .credential(credentials)
                .codecRegistry(pojoCodecRegistry)
                .build();

        // Initialize MongoClient
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            // Access the database
            MongoDatabase bankSystemDB = mongoClient.getDatabase("bankSystem");

            // Print database name to confirm connection
            System.out.println("Connected to database: " + bankSystemDB.getName());
        } catch (Exception e) {
            System.err.println("Failed to connect to MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
