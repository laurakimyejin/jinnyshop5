package com.jinnyshop5.member.model;

import com.jinnyshop5.member.constant.Role;
import com.jinnyshop5.member.dto.MemberFormDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="User")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member implements UserDetails {

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

    @Builder
    public Member(Long id, String memberName, String password, String email, String phoneNumber, Role role, String nickname, String address) {
        this.id = id;
        this.memberName = memberName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.nickname = nickname;
        this.address = address;
    }


    public Member update(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public MemberFormDto toDomain() {
        return MemberFormDto.builder()
                .memberName(memberName)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .address(address)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
