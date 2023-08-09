package com.jinnyshop5.member.dto;

/*회원가입시 넘어오는 가입정보 담는 dto*/

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFormDto {

    private String userName;

    private String email;

    private String password;

    private String address;
}
