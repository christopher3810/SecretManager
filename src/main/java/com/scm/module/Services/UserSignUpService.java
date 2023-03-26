package com.scm.module.Services;

import com.scm.module.Connection.VaultWebClient;
import com.scm.module.Exception.UserAlreadyExistsException;
import com.scm.module.Model.UserEntity;
import com.scm.module.Repository.UserRedisRepository;
import com.scm.module.Repository.UserRepository;
import com.scm.module.enums.Role;
import com.scm.module.validator.UserValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserSignUpService {

    private final UserRepository userRepository;
    private final UserRedisRepository userRedisRepository;
    private final PasswordEncoder passwordEncoder;
    private final VaultWebClient vaultWebClient;
    private final UserValidator userValidator;

    public UserSignUpService (UserRepository userRepository,
        UserRedisRepository userRedisRepository,
        PasswordEncoder passwordEncoder,
        VaultWebClient vaultWebClient,
        UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userRedisRepository = userRedisRepository;
        this.passwordEncoder = passwordEncoder;
        this.vaultWebClient = vaultWebClient;
        this.userValidator = userValidator;
    }

    public Mono<UserEntity> signUp(UserEntity user) {
        user.setRole(Role.ROLE_USER);
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

    private Mono<UserEntity> saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    // Creates a secret in the vault and caches the user
    private Mono<UserEntity> createSecretAndCacheUser(UserEntity savedUser) {
        String plainPassword = savedUser.getPassword();
        return vaultWebClient.createSecret(String.valueOf(savedUser.getId()), plainPassword)
            .then(userRedisRepository.save(savedUser))
            .thenReturn(savedUser);
    }

    private void validateEmail(String email) {
        userRepository.findByEmail(email)
            .hasElement()
            .subscribe(userExists -> {
                if (userExists) {
                    throw new UserAlreadyExistsException("User with email already exists");
                }
            });
    }

    private Mono<UserEntity> handleError(Throwable e) {
        if (e instanceof DataIntegrityViolationException) {
            return Mono.error(new UserAlreadyExistsException("User with email already exists"));
        }
        return Mono.error(e);
    }
}
