package com.jinnyshop5.Member.service;

import com.jinnyshop5.Member.model.Member;
import com.jinnyshop5.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    /*로그인 할 유저의 아이디를 파라미터로 받음*/
    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByMemberName(member.getMemberName());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String memberName) throws UsernameNotFoundException{
        Member member = memberRepository.findByMemberName(memberName);

        if(member == null){
            throw new UsernameNotFoundException(memberName);
        }

        //user 객체 반환 아이디, 비번, role 넘겨줌
        return User.builder()
                .username(member.getMemberName())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }


}
