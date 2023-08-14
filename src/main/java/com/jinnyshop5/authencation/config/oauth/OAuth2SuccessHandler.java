package com.jinnyshop5.authencation.config.oauth;


import com.jinnyshop5.authencation.util.SuccessHandlerUtils;
import com.jinnyshop5.authencation.config.jwt.TokenProvider;
import com.jinnyshop5.member.model.Member;
import com.jinnyshop5.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jinnyshop5.authencation.util.SuccessHandlerUtils.ACCESS_TOKEN_DURATION;
import static com.jinnyshop5.authencation.util.SuccessHandlerUtils.REFRESH_TOKEN_DURATION;

@RequiredArgsConstructor
@Component("OAuth2SuccessHandler")
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final SuccessHandlerUtils successHandlerUtils;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final MemberService memberService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Member member = memberService.findByEmail((String) oAuth2User.getAttributes().get("email"));

        String refreshToken = tokenProvider.generateToken(member, REFRESH_TOKEN_DURATION);
        successHandlerUtils.saveRefreshToken(member.getId(), refreshToken);
        successHandlerUtils.addRefreshTokenToCookie(request, response, refreshToken);

        String accessToken = tokenProvider.generateToken(member, ACCESS_TOKEN_DURATION);
        successHandlerUtils.addAccessTokenToCookie(request, response, accessToken);
        String targetUrl = successHandlerUtils.getTargetUrl(accessToken);

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }


}
