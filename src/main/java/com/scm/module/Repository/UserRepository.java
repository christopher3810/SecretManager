package com.scm.module.Repository;

import com.scm.module.Model.UserEntity;
import java.util.Optional;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {

    @Query("SELECT * FROM users WHERE email_address = :email")
    Mono<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndEnabled(String email, boolean enabled);

}
