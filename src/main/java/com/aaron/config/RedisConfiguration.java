package com.aaron.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Aaron Sheng on 9/18/16.
 */
@Configuration
public class RedisConfiguration {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.pool.maxTotal}")
    private int maxTotal;
    @Value("${spring.redis.pool.maxIdle}")
    private int maxIdle;
    @Value("${spring.redis.pool.minIdle}")
    private int minIdle;
    @Value("${spring.redis.pool.maxWaitMillis}")
    private long maxWaitMillis;
    @Value("${spring.redis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);

        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setPassword(password);
        factory.setTimeout(timeout);
        factory.setPoolConfig(jedisPoolConfig);
        return factory;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(this.redisConnectionFactory());
        return redisTemplate;
    }
}
