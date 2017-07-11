package com.aaron.redis;

public interface UserRedis {
    void add(String id, String ip);

    void update(final String id, final String ip);

    void delete(final String id);

    Boolean has(final String id);

    String get(final String id);
}
