package com.scm.module.Services;

import com.scm.module.Connection.VaultWebClient;
import com.scm.module.Repository.UserRepository;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserDataService {

    private final VaultOperations vaultOperations;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserRepository userRepository;
    private final VaultWebClient vaultWebClient;

    public Mono<Void> encryptUserData(String email, Map<String, Object> userData) {
        VaultTransitOperations transitOperations = vaultOperations.opsForTransit("transit");
        return transitOperations.encrypt("users", userData)
            .flatMap(encryptedData -> saveEncryptedUserData(email, encryptedData));
    }

    private Mono<Void> saveEncryptedUserData(String email, Map<String, Object> encryptedData) {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8200/v1/secret").build();
        return webClient.put()
            .uri("users/{email}", email)
            .body(Mono.just(encryptedData), new ParameterizedTypeReference<Map<String, Object>>() {})
            .retrieve()
            .toBodilessEntity()
            .then();
    }

    private Mono<Map<String, Object>> decryptUserData(Map<String, Object> vaultData) {
        VaultTransitOperations transitOperations = vaultOperations.opsForTransit("transit");
        return transitOperations.decrypt("users", vaultData);
    }

    private Mono<Map<String, Object>> saveUserDataToCache(String email, Map<String, Object> decryptedData) {
        redisTemplate.opsForValue().set(email, decryptedData);
        return Mono.just(decryptedData);
    }

    private Mono<Void> saveUserDataToDatabase(String email, Map<String, Object> decryptedData) {
        //UserEntity 만들어서 postgres에 save
        userRepository.save(user);
//        return Mono.empty();
    }
}
