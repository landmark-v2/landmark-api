package com.landmark.app.service.impl;

import com.landmark.app.service.RedisService;
import com.landmark.app.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl extends LoggerUtils implements RedisService {

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean save(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("save : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.expire(key, timeout, timeUnit);
            return true;
        } catch (Exception e) {
            logger.error("expire : " + e.getMessage());
            return false;
        }
    }

    @Override
    public String get(String key) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            return value != null ? value.toString() : "";
        } catch (Exception e) {
            logger.error("get : " + e.getMessage());
            return "";
        }
    }
}
