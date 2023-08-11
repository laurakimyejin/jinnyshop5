package com.jinnyshop5.authencation.service;

import com.jinnyshop5.authencation.config.jwt.TokenProvider;
import com.jinnyshop5.member.model.Member;
import com.jinnyshop5.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberService memberService;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getMemberId();
        Member member = memberService.findById(userId);
        log.info("AccessToken reissue : [{}],[{}]",member.getMemberName(),member.getNickname());
        return tokenProvider.generateToken(member, Duration.ofHours(2));
    }
}
