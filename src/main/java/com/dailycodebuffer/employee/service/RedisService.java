package com.dailycodebuffer.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Set a value in Redis
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // Get a value from Redis
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Delete a key in Redis
    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    // Add to a list in Redis
    public void addToList(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    // Get list elements from Redis
    public Object getList(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    // Other operations can be added as needed...
}