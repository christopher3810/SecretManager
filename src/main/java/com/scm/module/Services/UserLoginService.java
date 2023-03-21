package com.scm.module.Services;

import com.scm.module.Repository.UserRedisRepository;
import com.scm.module.Repository.UserRepository;
import com.scm.module.validator.UserValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    public UserLoginService(UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        UserValidator userValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    // The loginUser code from UserManagementService
}