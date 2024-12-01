package org.example.redis;

import redis.clients.jedis.JedisPooled;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RedisClient {

    private static JedisPooled jedis;

    static {
        try (InputStream input = RedisClient.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            String host = prop.getProperty("redis.host");
            int port = Integer.parseInt(prop.getProperty("redis.port"));
            jedis = new JedisPooled(host, port);
        } catch (IOException ex) {
            ex.printStackTrace();
            jedis = new JedisPooled("localhost", 6379);
        }
    }

    public static JedisPooled getJedis() {
        return jedis;
    }
}
