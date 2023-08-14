package com.jinnyshop5.authencation.controller.api;


import com.jinnyshop5.authencation.dto.request.CreateAccessTokenRequest;
import com.jinnyshop5.authencation.dto.request.DeleteRefreshTokenRequest;
import com.jinnyshop5.authencation.dto.response.CreateAccessTokenResponse;
import com.jinnyshop5.authencation.service.RefreshTokenService;
import com.jinnyshop5.authencation.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
            @RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
    @DeleteMapping("/api/token")
    public ResponseEntity<?> deleteRefreshToken(
            Principal principal,
            @RequestBody DeleteRefreshTokenRequest deleteRequest){

        if (principal != null) {
            log.info(principal.getName());
            refreshTokenService.deleteRefreshToken(principal.getName());
        }

        if (deleteRequest != null) {
            log.info(deleteRequest.getRefreshToken());
            refreshTokenService.deleteRefreshToken(deleteRequest);
        }
        return new ResponseEntity<>(HttpStatus.OK);


    }
}

