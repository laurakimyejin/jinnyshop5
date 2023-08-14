package com.jinnyshop5.authencation.config;


import com.jinnyshop5.authencation.config.jwt.TokenProvider;
import com.jinnyshop5.authencation.util.SuccessHandlerUtils;
import com.jinnyshop5.member.model.Member;
import com.jinnyshop5.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jinnyshop5.authencation.util.SuccessHandlerUtils.ACCESS_TOKEN_DURATION;
import static com.jinnyshop5.authencation.util.SuccessHandlerUtils.REFRESH_TOKEN_DURATION;

@RequiredArgsConstructor
@Component ("SuccessHandler")
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final SuccessHandlerUtils successHandlerUtils;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Member member = memberService.findByEmail(authentication.getName());

        String refreshToken = tokenProvider.generateToken(member, REFRESH_TOKEN_DURATION);
        successHandlerUtils.saveRefreshToken(member.getId(), refreshToken);
        successHandlerUtils.addRefreshTokenToCookie(request, response, refreshToken);

        String accessToken = tokenProvider.generateToken(member, ACCESS_TOKEN_DURATION);
        successHandlerUtils.addAccessTokenToCookie(request, response, accessToken);
        String targetUrl = successHandlerUtils.getTargetUrl(accessToken);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
