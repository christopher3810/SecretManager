package com.scm.module.DTO;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private String castle;
    private String email;
    @Setter
    private String password;
    private LocalDate birthday;
    private String phoneNumber;
    private String address;
    private String city;
    private boolean trial;
    private String nation;

}
