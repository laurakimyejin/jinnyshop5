package com.jinnyshop5.member.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginRequestDto {

    private String memberName;
    private String password;

}
