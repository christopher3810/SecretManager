package com.scm.module.Controller;

import com.scm.module.Services.UserManagementService;
import com.scm.module.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final UserManagementService userManagementService;

    public UserController(UserManagementService userService) {
        this.userManagementService = userService;
    }

    @PostMapping("/signup")
    public Mono<Void> signUp(@RequestBody UserDto userDto) {
        //dto convert to entity 필요
        return userManagementService.signUp(userDto);
    }
}


