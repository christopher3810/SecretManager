package com.scm.module.DTO;

import com.scm.module.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private String email;
    private String token;
    private String name;
    private Role role;
}
