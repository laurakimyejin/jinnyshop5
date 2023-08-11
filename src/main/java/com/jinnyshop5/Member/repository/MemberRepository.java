package com.jinnyshop5.Member.repository;

import com.jinnyshop5.Member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /*회원가입시 중복된 회원 있는지 검사*/
    Member findByMemberName(String memberName);
}
