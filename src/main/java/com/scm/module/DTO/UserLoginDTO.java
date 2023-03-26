package com.scm.module.DTO;

import com.scm.module.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
    private String email;
    private String password;
    private Role role;
}
