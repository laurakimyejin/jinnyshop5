package com.jinnyshop5.authencation.config;

import com.jinnyshop5.authencation.config.jwt.TokenProvider;
import com.jinnyshop5.authencation.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.jinnyshop5.authencation.config.oauth.OAuth2SuccessHandler;
import com.jinnyshop5.authencation.config.oauth.OAuth2UserCustomService;
import com.jinnyshop5.authencation.repository.RefreshTokenRepository;
import com.jinnyshop5.authencation.util.SuccessHandlerUtils;
import com.jinnyshop5.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SuccessHandlerUtils successHandlerUtils;
    private final MemberService memberService;


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/api/token").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll();

        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("memberName")
                .failureUrl("/members/login/error")
                .successHandler(successHandler());

        http.oauth2Login()
                .loginPage("/members/login")
                .authorizationEndpoint()
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .successHandler(oAuth2SuccessHandler())
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService);

        http.logout()
                .logoutUrl("/members/logout")
                .deleteCookies("access_token","refresh_token")
                .logoutSuccessUrl("/members/login");

        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**"));

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler () {
        return new OAuth2SuccessHandler(tokenProvider,
                successHandlerUtils,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                memberService
        );
    }

    @Bean
    public LoginSuccessHandler successHandler () {
        return new LoginSuccessHandler(tokenProvider,
                successHandlerUtils,
                memberService
        );
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter () {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository () {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }


}


