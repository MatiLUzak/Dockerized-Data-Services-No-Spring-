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

        // Tworzymy obiekt JSON, który będzie zawierał dwa pola:
        // "className" - pełna nazwa klasy obiektu (np. "org.example.model.Beletrystyka")
        // "data" - zserializowany obiekt wolumin
        String className = wolumin.getClass().getName();

        // Tworzymy obiekt JSON "wrapper"
        com.google.gson.JsonObject wrapper = new com.google.gson.JsonObject();
        wrapper.addProperty("className", className);
        wrapper.add("data", gson.toJsonTree(wolumin));

        try (Jedis jedis = RedisClient.getJedis()) {
            jedis.setex(key, 86400, gson.toJson(wrapper));
        }
    }


    @Override
    public Wolumin znajdzWolumin(ObjectId id) {
        String key = "wolumin:" + id.toHexString();
        try (Jedis jedis = RedisClient.getJedis()) {
            String cachedWolumin = jedis.get(key);
            if (cachedWolumin != null) {
                com.google.gson.JsonObject wrapper = gson.fromJson(cachedWolumin, com.google.gson.JsonObject.class);

                String className = wrapper.get("className").getAsString();

                com.google.gson.JsonElement data = wrapper.get("data");


                Class<?> cls = Class.forName(className);

                Object obj = gson.fromJson(data, cls);

                return (Wolumin) obj;
            } else {
                Wolumin wolumin = repozytorium.znajdzWolumin(id);
                if (wolumin != null) {
                    String className = wolumin.getClass().getName();

                    com.google.gson.JsonObject wrapper = new com.google.gson.JsonObject();
                    wrapper.addProperty("className", className);
                    wrapper.add("data", gson.toJsonTree(wolumin));

                    jedis.setex(key, 86400, gson.toJson(wrapper));
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
    }

    public void clearCache() {
        try (Jedis jedis = RedisClient.getJedis()) {
            jedis.flushDB();
        } catch (Exception e) {
            System.err.println("Błąd podczas czyszczenia cache'a: " + e.getMessage());
        }
    }
}
