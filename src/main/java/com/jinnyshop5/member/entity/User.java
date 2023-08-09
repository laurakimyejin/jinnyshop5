package com.jinnyshop5.member.entity;

import com.jinnyshop5.member.constant.Role;
import com.jinnyshop5.member.dto.UserFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="User")
@Getter
@Setter
@ToString
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="user_name")
    private String userName;//아이디

    @Column(name = "password")
    private String password;

    @Column(unique = true)
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String nickname;//이름

    private String address;

    public static User createUser(UserFormDto userFormDto,
                                  PasswordEncoder passwordEncoder){
        User user = new User();
        user.setUserName(userFormDto.getUserName());
        user.setEmail(userFormDto.getEmail());
        user.setAddress(userFormDto.getAddress());
        String password = passwordEncoder.encode(userFormDto.getPassword());
        user.setPassword(password);
        user.setRole(Role.CONSUMER);
        return user;
    }
}
