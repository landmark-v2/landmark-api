package com.landmark.app.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    boolean save(String key, String value);

    boolean expire(String key, long timeout, TimeUnit timeUnit);

    String get(String key);

}
