package com.scm.module.Config.SMMConfig;

import com.scm.module.Connection.VaultWebClient;
import com.scm.module.Model.UserEntity;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties({ExternalVaultProperties.class, ExternalRedisProperties.class, ExternalPostgresProperties.class})
public class SMMConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public <T> ReactiveRedisTemplate<String, T> reactiveRedisTemplate(
        ReactiveRedisConnectionFactory factory, Class<T> clazz) {
        RedisSerializationContext<String, T> serializationContext = RedisSerializationContext
            .<String, T>newSerializationContext(new StringRedisSerializer())
            .value(new Jackson2JsonRedisSerializer<>(clazz))
            .build();
        return new ReactiveRedisTemplate<>(factory, serializationContext);
    }

    @Bean
    public ReactiveRedisTemplate<String, String> reactiveStringRedisTemplate(
        ReactiveRedisConnectionFactory factory) {
        return reactiveRedisTemplate(factory, String.class);
    }

    @Bean
    public ReactiveRedisTemplate<String, UserEntity> reactiveUserRedisTemplate(
        ReactiveRedisConnectionFactory factory) {
        return reactiveRedisTemplate(factory, UserEntity.class);
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public VaultWebClient vaultWebClient(WebClient.Builder webClientBuilder, ExternalVaultProperties vaultProperties) {
        return new VaultWebClient(webClientBuilder, vaultProperties.getUrl());
    }

}
