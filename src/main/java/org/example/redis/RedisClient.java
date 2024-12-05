package org.example.redis;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RedisClient {

    private static String host;
    private static int port;

    static {
        try (InputStream input = RedisClient.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            host = prop.getProperty("redis.host", "localhost");
            port = Integer.parseInt(prop.getProperty("redis.port", "6379"));
        } catch (IOException ex) {
            ex.printStackTrace();
            host = "localhost";
            port = 6379;
        }
    }

    public static Jedis getJedis() {
        return new Jedis(host, port);
    }

    public static String getHost() {
        return host;
    }

    public static int getPort() {
        return port;
    }
}
