import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnectionTest {
    public static void main(String[] args) {
        String connectionString = "mongodb://admin:adminpassword@localhost:27017,localhost:27018,localhost:27019/?replicaSet=replica_set_single&authSource=admin";

        try {
            ConnectionString connString = new ConnectionString(connectionString);

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .build();

            MongoClient mongoClient = MongoClients.create(settings);

            MongoDatabase database = mongoClient.getDatabase("BookSystem");

            System.out.println("Collections in database 'BookSystem':");
            database.listCollectionNames().forEach(System.out::println);

            mongoClient.close();
        } catch (Exception e) {
            System.err.println("Błąd połączenia z MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
