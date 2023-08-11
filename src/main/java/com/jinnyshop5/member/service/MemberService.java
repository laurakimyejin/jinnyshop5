package com.jinnyshop5.member.service;

import com.jinnyshop5.member.constant.Role;
import com.jinnyshop5.member.dto.request.AddMemberRequestDto;
import com.jinnyshop5.member.dto.request.MemberLoginRequestDto;
import com.jinnyshop5.member.model.Member;
import com.jinnyshop5.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    public String memberLogin(MemberLoginRequestDto requestDto) {

        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(
                        new UsernamePasswordAuthenticationToken(
                                requestDto.getMemberName(), requestDto.getPassword()
                        )
                );
        return "";
    }

    public void memberSave(AddMemberRequestDto dto) {

        dto.toEntity();
        String encodePassword = encoder.encode(dto.getPassword());

        Member member = dto.toEntity();
        member.setPassword(encodePassword);
        member.setRole(Role.SELLER);
        memberRepository.save(member);

    }

    public Member findByMemberName(String memberName) {
        return memberRepository.findByMemberName(memberName)
                .orElseThrow(IllegalAccessError::new);
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        return memberRepository.findByMemberName(email)
                .orElseThrow(()-> new IllegalArgumentException((email)));
        //user 객체 반환 아이디, 비번, role 넘겨줌
//        return User.builder()
//                .username(member.getMemberName())
//                .password(member.getPassword())
//                .roles(member.getRole().toString())
//                .build();
    }
}
