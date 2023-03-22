package com.scm.module.Services;

import com.scm.module.DTO.LoginResponseDTO;
import com.scm.module.DTO.UserLoginDTO;
import com.scm.module.Model.UserEntity;
import com.scm.module.Repository.UserRepository;
import com.scm.module.Utils.Jwt.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Service
public class UserLoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCacheService userCacheService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserSessionService userSessionService;

    public UserLoginService(UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        UserCacheService userCacheService,
        UserSessionService userSessionService,
        JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userCacheService = userCacheService;
        this.userSessionService = userSessionService;
        this.jwtTokenUtil = jwtTokenUtil;

    }

    public Mono<LoginResponseDTO> loginUser(UserLoginDTO userLoginDTO) {
        return userCacheService.findByEmail(userLoginDTO.getEmail())
            .switchIfEmpty(fetchAndCacheUser(userLoginDTO.getEmail()))
            .flatMap(user -> checkCredentials(userLoginDTO, user))
            .flatMap(user -> handleUserSession(user))
            .map(this::createLoginResponseDTO);
    }

    private Mono<UserEntity> fetchAndCacheUser(String email) {
        return userRepository.findByEmail(email)
            .flatMap(userCacheService::cacheUser);
    }

    private Mono<UserEntity> checkCredentials(UserLoginDTO userLoginDTO, UserEntity user) {
        if (passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            return Mono.just(user);
        } else {
            return Mono.error(new RuntimeException("Invalid credentials"));
        }
    }

    private Mono<Tuple2<UserEntity, String>> handleUserSession(UserEntity user) {
        return userSessionService.invalidateSession(user.getId().toString())
            .then(Mono.fromCallable(() -> jwtTokenUtil.generateToken(user)))
            .flatMap(token -> userSessionService.storeSession(user.getId().toString(), token)
                .thenReturn(Tuples.of(user, token)));
    }


    private LoginResponseDTO createLoginResponseDTO(Tuple2<UserEntity, String> userAndToken) {
        UserEntity user = userAndToken.getT1();
        String token = userAndToken.getT2();
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setEmail(user.getEmail());
        loginResponseDTO.setName(user.getName());
        loginResponseDTO.setToken(token);
        // Set any other necessary user information in the response DTO
        return loginResponseDTO;
    }
}