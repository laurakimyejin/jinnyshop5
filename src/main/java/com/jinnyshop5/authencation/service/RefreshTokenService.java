package com.jinnyshop5.authencation.service;

import com.jinnyshop5.authencation.domain.RefreshToken;
import com.jinnyshop5.authencation.dto.request.DeleteRefreshTokenRequest;
import com.jinnyshop5.authencation.repository.RefreshTokenRepository;
import com.jinnyshop5.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberService memberService;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
    @Transactional
    public void deleteRefreshToken(String email) {
        refreshTokenRepository.deleteByMemberId(
                memberService.findByEmail(email).getId());
    }
    @Transactional
    public void deleteRefreshToken(DeleteRefreshTokenRequest token) {
        refreshTokenRepository.deleteByRefreshToken(token.getRefreshToken());
    }

}


