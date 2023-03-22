package com.scm.module.Controller;

import com.scm.module.DTO.UserDTO;
import com.scm.module.Mapper.UserMapper;
import com.scm.module.Model.UserEntity;
import com.scm.module.DTO.UserLoginDTO;
import com.scm.module.Services.UserLoginService;
import com.scm.module.Services.UserSignUpService;
import com.scm.module.validator.UserValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserSignUpService userSignUpService;
    private final UserLoginService userLoginService;
    private final UserValidator userValidator;
    private final UserMapper userMapper;


    public UserController(UserSignUpService userSignUpService,
        UserLoginService userLoginService,
        UserValidator userValidator,
        UserMapper userMapper) {
        this.userSignUpService = userSignUpService;
        this.userLoginService = userLoginService;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<UserDTO>> signUp(@RequestBody UserDTO userDTO) {
        userValidator.validateUserSignup(userDTO);
        UserEntity user = userMapper.convertToUserEntity(userDTO);
        return userSignUpService.signUp(user)
            .map(userMapper::convertToUserDTO)
            .map(savedUser -> ResponseEntity.ok(savedUser))
            .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping
    public Mono<ResponseEntity<UserDTO>> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return userLoginService.loginUser(userLoginDTO)
            .map(userEntity -> {
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(userEntity, userDTO);
                userDTO.setPassword(null); // Remove password from response
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            })
            .onErrorResume(e -> Mono.just(new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED)));
    }
}


