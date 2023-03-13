package com.scm.module;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserDto {

    private String name;
    private String castle;
    private String emailAddress;
    private String password;
    private LocalDate birthday;
    private String phoneNumber;
    private String address;
    private String city;
    private boolean trial;
    private String nation;

}