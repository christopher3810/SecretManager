package com.scm.module.Services;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserSessionService {
    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public Mono<Boolean> invalidateSession(String userId) {
        return reactiveRedisTemplate.opsForValue().delete(getSessionKey(userId)).map(result -> result != null && result);
    }

    public Mono<Boolean> storeSession(String userId, String token) {
        String key = getSessionKey(userId);
        return reactiveRedisTemplate.opsForValue().set(key, token)
            .flatMap(result -> reactiveRedisTemplate.expire(key, Duration.ofHours(1)))
            .map(result -> result != null && result);
    }

    private String getSessionKey(String userId) {
        return "user:session:" + userId;
    }
}
