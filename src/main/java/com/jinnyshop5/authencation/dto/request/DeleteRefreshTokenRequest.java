package com.jinnyshop5.authencation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRefreshTokenRequest {
    private String refreshToken;
    private String subject;
}
