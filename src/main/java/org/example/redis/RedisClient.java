package org.example.redis;

import redis.clients.jedis.Jedis;

public class RedisClient {

    private static String host;
    private static int port;

    static {
        host = "localhost"; // Możesz wczytać z pliku konfiguracyjnego, jeśli chcesz
        port = 6379;
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
