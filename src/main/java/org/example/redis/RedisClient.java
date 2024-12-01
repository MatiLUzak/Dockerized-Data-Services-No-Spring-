package org.example.redis;

import redis.clients.jedis.JedisPooled;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RedisClient {

    private static JedisPooled jedis;
    private static String host;
    private static int port;

    static {
        try (InputStream input = RedisClient.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            host = prop.getProperty("redis.host");
            port = Integer.parseInt(prop.getProperty("redis.port"));
            jedis = new JedisPooled(host, port);
        } catch (IOException ex) {
            ex.printStackTrace();
            host = "localhost";
            port = 6379;
            jedis = new JedisPooled(host, port);
        }
    }

    public static JedisPooled getJedis() {
        return jedis;
    }

    public static String getHost() {
        return host;
    }

    public static int getPort() {
        return port;
    }
}

