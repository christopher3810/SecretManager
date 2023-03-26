package com.scm.module.Model;


import com.scm.module.enums.Role;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id @Tsid
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    private String name;

    private String castle;

    @Column(name = "email")
    private String email;

    @Setter
    private String password;

    private LocalDate birthday;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    private String city;

    private boolean trial;

    private String nation;

}
