package com.jinnyshop5.Member.dto;

/*회원가입시 넘어오는 가입정보 담는 dto*/

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message="아이디는 필수 입력값입니다.")
    private String memberName;//null x

    private String nickname;

    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotEmpty(message = "전화번호는 필수입력값입니다.")
    private String phoneNumber;//null x

    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;//null x

    @NotEmpty(message = "주소는 필수 입력값입니다.")
    private String address;//null x
}
