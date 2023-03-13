package com.scm.module.Repository;

import com.scm.module.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Lombok annotation to indicate that this class is a Spring Data repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmailAddress(String emailAddress);

}
