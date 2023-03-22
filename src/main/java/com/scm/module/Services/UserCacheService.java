package com.scm.module.Services;

import com.scm.module.Model.UserEntity;
import com.scm.module.Repository.UserRedisRepository;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserCacheService {
    @Autowired
    private UserRedisRepository userRedisRepository;

    @Autowired
    private ReactiveRedisTemplate<String, UserEntity> reactiveRedisTemplate;

    private final Duration cacheExpiration = Duration.ofHours(1); // Adjust according to your needs

    public Mono<UserEntity> findByEmail(String email) {
        return userRedisRepository.findByEmail(email);
    }

    public Mono<UserEntity> cacheUser(UserEntity user) {
        return userRedisRepository.save(user)
            .flatMap(savedUser -> reactiveRedisTemplate.expire(getCacheKey(savedUser.getId()), cacheExpiration)
                .thenReturn(savedUser));
    }

    private String getCacheKey(Long id) {
        return "user:id:" + id;
    }
}
