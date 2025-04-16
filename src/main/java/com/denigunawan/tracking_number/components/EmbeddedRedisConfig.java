//package com.denigunawan.tracking_number.components;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import org.springframework.context.annotation.Configuration;
//import redis.embedded.RedisServer;
//
//@Configuration
//public class EmbeddedRedisConfig {
//
//    private RedisServer redisServer;
//
//    @PostConstruct
//    public void startRedis() {
//        redisServer = new RedisServer(6379);
//        redisServer.start();
//    }
//
//    @PreDestroy
//    public void stopRedis() {
//        if (redisServer != null) {
//            redisServer.stop();
//        }
//    }
//
//}
