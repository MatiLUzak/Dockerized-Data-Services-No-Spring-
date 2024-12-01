package org.example.databaserepository;

import com.google.gson.Gson;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.Wolumin;
import org.example.redis.RedisClient;
import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WoluminCacheRepositoryTest {

    private WoluminCacheRepository cacheRepository;
    private WoluminRepository mockRepository;
    private Gson gson;

    @BeforeEach
    void setUp() {
        mockRepository = mock(WoluminRepository.class);
        cacheRepository = new WoluminCacheRepository(mockRepository);
        gson = new Gson();

        try (Jedis jedis = RedisClient.getJedis()) {
            jedis.flushDB();
        }
    }

    @Test
    void testDodajWoluminDoCache() {
        ObjectId id = new ObjectId();
        Wolumin wolumin = new Wolumin("Wydawnictwo ABC", "Polski", "Tytuł XYZ");
        wolumin.setId(id);


        cacheRepository.dodajWolumin(wolumin);

        String key = "wolumin:" + id.toHexString();
        try (Jedis jedis = RedisClient.getJedis()) {
            String cachedData = jedis.get(key);
            assertNotNull(cachedData, "Dane woluminu nie zostały zapisane w cache'u.");
            Wolumin cachedWolumin = gson.fromJson(cachedData, Wolumin.class);
            assertEquals(wolumin.getTytul(), cachedWolumin.getTytul(), "Tytuł woluminu w cache'u nie zgadza się.");
        }
    }

    @Test
    void testZnajdzWolumin_CacheHit() {
        ObjectId id = new ObjectId();
        Wolumin wolumin = new Wolumin("Wydawnictwo ABC", "Polski", "Tytuł XYZ");
        wolumin.setId(id);

        String key = "wolumin:" + id.toHexString();
        try (Jedis jedis = RedisClient.getJedis()) {
            jedis.setex(key, 86400, gson.toJson(wolumin));
        }

        Wolumin retrieved = cacheRepository.znajdzWolumin(id);

        assertNotNull(retrieved, "Nie udało się pobrać woluminu z cache'a.");
        assertEquals(wolumin.getTytul(), retrieved.getTytul(), "Tytuł woluminu nie zgadza się.");
        verify(mockRepository, times(0)).znajdzWolumin(id);
    }

    @Test
    void testZnajdzWolumin_CacheMiss() {
        ObjectId id = new ObjectId();
        Wolumin wolumin = new Wolumin("Wydawnictwo ABC", "Polski", "Tytuł XYZ");
        wolumin.setId(id);

        when(mockRepository.znajdzWolumin(id)).thenReturn(wolumin);

        Wolumin retrieved = cacheRepository.znajdzWolumin(id);

        assertNotNull(retrieved, "Nie udało się pobrać woluminu z bazy danych.");
        assertEquals(wolumin.getTytul(), retrieved.getTytul(), "Tytuł woluminu nie zgadza się.");
        verify(mockRepository, times(1)).znajdzWolumin(id);

        String key = "wolumin:" + id.toHexString();
        try (Jedis jedis = RedisClient.getJedis()) {
            String cachedData = jedis.get(key);
            assertNotNull(cachedData, "Dane woluminu nie zostały zapisane w cache'u po cache miss.");
        }
    }


    @Test
    void testZnajdzWolumin_RedisConnectionLost() {
        ObjectId id = new ObjectId();
        Wolumin wolumin = new Wolumin("Wydawnictwo", "Polski", "Tytuł");
        wolumin.setId(id);

        when(mockRepository.znajdzWolumin(id)).thenReturn(wolumin);

        RedisClient.getJedis().close();

        Wolumin retrieved = cacheRepository.znajdzWolumin(id);

        assertNotNull(retrieved, "Nie udało się pobrać danych z MongoDB po utracie połączenia z Redisem.");
        verify(mockRepository, times(1)).znajdzWolumin(id);
    }


    @Test
    void testUsunWolumin() {
        ObjectId id = new ObjectId();
        Wolumin wolumin = new Wolumin("Wydawnictwo", "Polski", "Tytuł");
        wolumin.setId(id);

        String key = "wolumin:" + id.toHexString();
        try (Jedis jedis = RedisClient.getJedis()) {
            jedis.setex(key, 86400, gson.toJson(wolumin));
        }

        cacheRepository.usunWolumin(id);

        try (Jedis jedis = RedisClient.getJedis()) {
            String cachedData = jedis.get(key);
            assertNull(cachedData, "Dane woluminu nie zostały usunięte z cache'a.");
        }
        verify(mockRepository, times(1)).usunWolumin(id);
    }

    @Test
    void testClearCache() {
        for (int i = 0; i < 5; i++) {
            ObjectId id = new ObjectId();
            Wolumin wolumin = new Wolumin("Wydawnictwo " + i, "Polski", "Tytuł " + i);
            wolumin.setId(id);

            String key = "wolumin:" + id.toHexString();
            try (Jedis jedis = RedisClient.getJedis()) {
                jedis.setex(key, 86400, gson.toJson(wolumin));
            }
        }

        cacheRepository.clearCache();

        try (Jedis jedis = RedisClient.getJedis()) {
            Long dbSize = jedis.dbSize();
            assertEquals(0L, dbSize, "Cache nie został poprawnie wyczyszczony.");
        }
    }



}
