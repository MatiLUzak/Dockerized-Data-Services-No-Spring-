package org.example.databaserepository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.databaserepository.WoluminCacheRepository;
import org.example.databaserepository.WoluminMongoRepository;
import org.example.model.Wolumin;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class WoluminRepositoryBenchmark {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private WoluminCacheRepository cacheRepository;
    private WoluminMongoRepository mongoRepository;
    private ObjectId testId;

    @Setup(Level.Trial)
    public void setUp() {
        // Inicjalizacja połączenia z MongoDB
        MongoCredential credentials = MongoCredential.createCredential(
                "admin", "admin", "adminpassword".toCharArray()
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credentials)
                .applyConnectionString(new ConnectionString("mongodb://mongodb1:27017," +
                        "mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single"))
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("BookSystem");
        mongoRepository = new WoluminMongoRepository(database.getCollection("woluminy", Document.class));
        cacheRepository = new WoluminCacheRepository(mongoRepository);

        Wolumin wolumin = new Wolumin("Wydawnictwo Test", "Polski", "Tytuł Testowy");
        mongoRepository.dodajWolumin(wolumin);
        testId = wolumin.getId();

        cacheRepository.clearCache();
    }

    @TearDown(Level.Trial)
    public void tearDown() {
        if (mongoClient != null) {
            mongoClient.close();
        }
        cacheRepository.clearCache();
    }

    @Setup(Level.Invocation)
    public void setupInvocation() {
        cacheRepository.znajdzWolumin(testId);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testCacheHit() {
        cacheRepository.znajdzWolumin(testId);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testCacheMiss() {
        cacheRepository.clearCache();
        cacheRepository.znajdzWolumin(testId);
    }
}


