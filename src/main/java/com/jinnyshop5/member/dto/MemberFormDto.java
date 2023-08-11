package com.jinnyshop5.member.dto;

/*회원가입시 넘어오는 가입정보 담는 dto*/

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class MemberFormDto {

    @NotBlank(message="아이디는 필수 입력값입니다.")
    private String memberName;//null x

    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;//null x

    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotEmpty(message = "전화번호는 필수입력값입니다.")
    private String phoneNumber;//null x

    private String nickname;

    @NotEmpty(message = "주소는 필수 입력값입니다.")
    private String address;//null x

    @Builder
    public MemberFormDto(String memberName, String password, String email, String phoneNumber, String nickname, String address) {
        this.memberName = memberName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.address = address;
    }

    public MemberFormDto toEntity() {
        return MemberFormDto.builder()
                .memberName(memberName)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .address(address)
                .build();
    }
}
