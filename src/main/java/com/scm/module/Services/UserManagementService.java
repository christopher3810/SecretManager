package com.scm.module.Services;

import com.scm.module.Connection.VaultWebClient;
import com.scm.module.Exception.UserAlreadyExistsException;
import com.scm.module.Model.UserEntity;
import com.scm.module.Repository.UserRedisRepository;
import com.scm.module.Repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

public class UserManagementService {

    private final UserRepository userRepository;
    private final UserRedisRepository userRedisRepository;
    private final PasswordEncoder passwordEncoder;
    private final VaultWebClient vaultWebClient;

    public UserManagementService (UserRepository userRepository,
        UserRedisRepository userRedisRepository,
        PasswordEncoder passwordEncoder,
        VaultWebClient vaultWebClient) {
        this.userRepository = userRepository;
        this.userRedisRepository = userRedisRepository;
        this.passwordEncoder = passwordEncoder;
        this.vaultWebClient = vaultWebClient;
    }

    public Mono<UserEntity> signUp(UserEntity user) {
        return Mono.defer(() -> {
            encodePassword(user);
            return userRepository.save(user)
                .flatMap(this::createSecretAndCacheUser)
                .onErrorResume(this::handleError);
        });
    }

    // Encodes the user's password
    private void encodePassword(UserEntity user) {
        String plainPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(plainPassword);
        user.setPassword(encodedPassword);
    }

    // Creates a secret in the vault and caches the user
    private Mono<UserEntity> createSecretAndCacheUser(UserEntity savedUser) {
        String plainPassword = savedUser.getPassword();
        return vaultWebClient.createSecret(String.valueOf(savedUser.getId()), plainPassword)
            .then(userRedisRepository.save(savedUser))
            .thenReturn(savedUser);
    }

    private Mono<UserEntity> handleError(Throwable e) {
        if (e instanceof DataIntegrityViolationException) {
            return Mono.error(new UserAlreadyExistsException("User with email already exists"));
        }
        return Mono.error(e);
    }
}
