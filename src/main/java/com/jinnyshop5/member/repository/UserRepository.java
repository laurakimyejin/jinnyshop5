package com.jinnyshop5.member.repository;

import com.jinnyshop5.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    /*회원가입시 중복된 회원 있는지 검사*/
    User findByEmail(String email);
}
