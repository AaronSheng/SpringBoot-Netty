package com.aaron.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRedisImpl implements UserRedis {
    private static final String ID_PREFIX = "user:";
    private static final String COLUMN_IP = "ip";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void add(String id, String ip) {
       redisTemplate.opsForHash().put(ID_PREFIX + id, COLUMN_IP, ip);
    }

    public void update(final String id, final String ip) {
        redisTemplate.opsForHash().put(ID_PREFIX + id, COLUMN_IP, ip);
    }

    public void delete(final String id) {
        redisTemplate.opsForHash().delete(ID_PREFIX + id);
    }

    public Boolean has(final String id) {
        return redisTemplate.opsForHash().hasKey(ID_PREFIX + id, COLUMN_IP);
    }

    public String get(final String id) {
        return (String) redisTemplate.opsForHash().get(ID_PREFIX + id, COLUMN_IP);
    }
}
