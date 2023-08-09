package com.jinnyshop5.Member.model;

import com.jinnyshop5.Member.constant.Role;
import com.jinnyshop5.Member.dto.MemberFormDto;
import com.jinnyshop5.model.BaseEntity;
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
public class Member extends BaseEntity {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="member_name")
    private String memberName;//아이디

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

    public static Member createUser(MemberFormDto memberFormDto,
                                    PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setMemberName(memberFormDto.getMemberName());
        member.setNickname(memberFormDto.getNickname());
        member.setEmail(memberFormDto.getEmail());
        member.setPhoneNumber(memberFormDto.getPhoneNumber());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.SELLER);
        return member;
    }
}
