package org.example;

import redis.clients.jedis.JedisPooled;

public class RedisTest {
    private static JedisPooled pool;

    public static void initConnection() {
        pool = new JedisPooled("localhost", 6379);
    }

    public static void main(String[] args) {
        initConnection();
        pool.set("key", "value");
        System.out.println("Stored value: " + pool.get("key"));
    }
}
