package org.example.databaserepository;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.Wolumin;
import org.example.redis.RedisClient;
import redis.clients.jedis.Jedis;
import com.google.gson.Gson;

public class WoluminCacheRepository implements WoluminRepository {

    private final WoluminRepository repozytorium;
    private final Gson gson;

    public WoluminCacheRepository(WoluminRepository repozytorium) {
        this.repozytorium = repozytorium;
        this.gson = new Gson();
    }

    @Override
    public void dodajWolumin(Wolumin wolumin) {
        repozytorium.dodajWolumin(wolumin);
        String key = "wolumin:" + wolumin.getId().toHexString();
        try (Jedis jedis = RedisClient.getJedis()) {
            jedis.setex(key, 86400, gson.toJson(wolumin));
        }
    }

    @Override
    public Wolumin znajdzWolumin(ObjectId id) {
        String key = "wolumin:" + id.toHexString();
        try (Jedis jedis = RedisClient.getJedis()) {
            String cachedWolumin = jedis.get(key);
            if (cachedWolumin != null) {
                return gson.fromJson(cachedWolumin, Wolumin.class);
            } else {
                Wolumin wolumin = repozytorium.znajdzWolumin(id);
                if (wolumin != null) {
                    jedis.setex(key, 86400, gson.toJson(wolumin));
                }
                return wolumin;
            }
        } catch (Exception e) {
            System.err.println("Błąd połączenia z Redisem: " + e.getMessage());
            return repozytorium.znajdzWolumin(id);
        }
    }

    @Override
    public void zaktualizujWolumin(ObjectId id, Wolumin updatedWolumin) {
        repozytorium.zaktualizujWolumin(id, updatedWolumin);
        String key = "wolumin:" + id.toHexString();
        try (Jedis jedis = RedisClient.getJedis()) {
            jedis.setex(key, 86400, gson.toJson(updatedWolumin));
        }
    }

    @Override
    public void usunWolumin(ObjectId id) {
        repozytorium.usunWolumin(id);
        String key = "wolumin:" + id.toHexString();
        try (Jedis jedis = RedisClient.getJedis()) {
            jedis.del(key);
        }
    }

    @Override
    public void deleteMany(Document filter) {
        repozytorium.deleteMany(filter);
        // Jeśli to konieczne, usuń odpowiednie klucze z cache'a
    }

    public void clearCache() {
        try (Jedis jedis = RedisClient.getJedis()) {
            jedis.flushDB();
        } catch (Exception e) {
            System.err.println("Błąd podczas czyszczenia cache'a: " + e.getMessage());
        }
    }
}
