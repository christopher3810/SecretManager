package com.scm.module.Controller;

import com.scm.module.Services.UserService;
import com.scm.module.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public Mono<Void> signUp(@RequestBody UserDto userDto) {
        return userService.signUp(userDto);
    }
}


