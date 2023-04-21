package com.scm.module.Services;

import static org.mockito.Mockito.times;
import static reactor.core.publisher.Mono.when;

import com.scm.module.Model.UserEntity;
import com.scm.module.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class UserSignUpServiceTest {

    @InjectMocks
    private UserSignUpService userSignUpService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void signUpTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("password");

        when(userRepository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Mono.empty());

        Mono<UserEntity> result = userSignUpService.signUp(userEntity);

        StepVerifier.create(result)
            .expectNext(userEntity)
            .verifyComplete();

        //verify(userRepository, times(1)).save(userEntity);
    }

}